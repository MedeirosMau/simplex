package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.domain.tabularsolution.SolutionVariable.createBasicVariable;
import static br.helios.simplex.domain.tabularsolution.SolutionVariable.createNonBasicVariable;
import static br.helios.simplex.infrastructure.io.OutputData.message;

import java.util.ArrayList;
import java.util.List;

public class PivotOperationService {

	public TabularSolution pivot(TabularSolution previousSolution) {
		List<SolutionVariable> newSolutionVariables = new ArrayList<>(previousSolution.variables);

		message("@ PIVOT OPERATION").line().log();
		SolutionVariable basicVariableCandidate = previousSolution.getBasicVariableCandidate();
		message("Basic candidate: " + basicVariableCandidate.toString()).line().log();
		SolutionVariable nonBasicVariableCandidate = previousSolution.getNonBasicVariableCandidate(basicVariableCandidate);
		message("Non Basic candidate: " + nonBasicVariableCandidate.toString()).line().log();

		// Swap data of basic/non basic variables

		for (int index = 0; index < newSolutionVariables.size(); index++) {
			SolutionVariable solutionVariable = newSolutionVariables.get(index);

			if (solutionVariable.variable.id() == basicVariableCandidate.variable.id()) {
				newSolutionVariables.set(index, createBasicVariable(basicVariableCandidate.variable, nonBasicVariableCandidate.tableLine));
			}

			if (solutionVariable.variable.id() == nonBasicVariableCandidate.variable.id()) {
				newSolutionVariables.set(index, createNonBasicVariable(nonBasicVariableCandidate.variable));
			}
		}

		double[][] previousSimplexTable = previousSolution.simplexTable;
		double[][] newSimplexTable = previousSolution.copySimplexTable();

		// Update pivot line by pivot element

		int pivotColumnIndex = basicVariableCandidate.index;
		int pivotLineIndex = nonBasicVariableCandidate.tableLine;
		double pivotValue = previousSimplexTable[pivotLineIndex][pivotColumnIndex];

		for (int j = 0; j < newSimplexTable[pivotLineIndex].length; j++) {
			newSimplexTable[pivotLineIndex][j] = newSimplexTable[pivotLineIndex][j] / pivotValue;
		}

		// Other pivot column values must be zero

		for (int i = 0; i < newSimplexTable.length; i++) {
			if (i != pivotLineIndex) {
				double pivotColumnValue = newSimplexTable[i][pivotColumnIndex];
				// message(format("i: %d, value: %f", i, pivotColumnValue)).line().log();
				if (pivotColumnValue != 0) {
					double normalizer = (-1) * pivotColumnValue;
					// message(format(", normalizer: %f", normalizer)).line().log();
					for (int j = 0; j < newSimplexTable[i].length; j++) {
						double value = newSimplexTable[pivotLineIndex][j];
						newSimplexTable[i][j] = (normalizer * value) + newSimplexTable[i][j];
					}
				}
			}
		}

		return new TabularSolution(newSimplexTable, newSolutionVariables, previousSolution.objective);
	}
}
