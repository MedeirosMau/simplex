package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.domain.tabularsolution.SolutionVariable.createBasicVariable;
import static br.helios.simplex.domain.tabularsolution.SolutionVariable.createNonBasicVariable;
import static br.helios.simplex.infrastructure.io.OutputData.message;
import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.infrastructure.util.MathContextUtil;

public class PivotOperationService {

	public TabularSolution pivot(TabularSolution previousSolution) {
		List<SolutionVariable> newSolutionVariables = new ArrayList<>(previousSolution.variables);

		message("@ PIVOT OPERATION").line().log();

		SolutionVariable basicVariableCandidate = null;
		SolutionVariable nonBasicVariableCandidate = null;

		if (previousSolution.isDual) {
			nonBasicVariableCandidate = previousSolution.getNonBasicVariableCandidate(null);
			message("Non Basic candidate: " + nonBasicVariableCandidate.toString()).line().log();
			basicVariableCandidate = previousSolution.getBasicVariableCandidate(nonBasicVariableCandidate);
			if (basicVariableCandidate == null) {
				return previousSolution;
			}
			message("Basic candidate: " + basicVariableCandidate.toString()).line().log();
		} else {
			basicVariableCandidate = previousSolution.getBasicVariableCandidate(null);
			message("Basic candidate: " + basicVariableCandidate.toString()).line().log();
			nonBasicVariableCandidate = previousSolution.getNonBasicVariableCandidate(basicVariableCandidate);
			if (nonBasicVariableCandidate == null) {
				return previousSolution;
			}
			message("Non Basic candidate: " + nonBasicVariableCandidate.toString()).line().log();
		}

		message("Basic candidate: " + basicVariableCandidate.toString()).line().log();
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

		BigDecimal[][] previousSimplexTable = previousSolution.simplexTable;
		BigDecimal[][] newSimplexTable = previousSolution.copySimplexTable();

		// Update pivot line by pivot element

		int pivotColumnIndex = basicVariableCandidate.index;
		int pivotLineIndex = nonBasicVariableCandidate.tableLine;
		BigDecimal pivotValue = previousSimplexTable[pivotLineIndex][pivotColumnIndex];

		for (int j = 0; j < newSimplexTable[pivotLineIndex].length; j++) {
			if (newSimplexTable[pivotLineIndex][j].signum() != 0) {
				newSimplexTable[pivotLineIndex][j] = newSimplexTable[pivotLineIndex][j].divide(pivotValue, MATH_CONTEXT);
			}
		}

		// Other pivot column values must be zero

		for (int i = 0; i < newSimplexTable.length; i++) {
			if (i != pivotLineIndex) {
				BigDecimal pivotColumnValue = newSimplexTable[i][pivotColumnIndex];
				// message(format("i: %d, value: %f", i, pivotColumnValue)).line().log();
				if (pivotColumnValue.compareTo(ZERO) != 0) {
					BigDecimal normalizer = pivotColumnValue.negate(MathContextUtil.MATH_CONTEXT);
					// message(format(", normalizer: %f", normalizer)).line().log();
					for (int j = 0; j < newSimplexTable[i].length; j++) {
						BigDecimal value = newSimplexTable[pivotLineIndex][j];
						newSimplexTable[i][j] = (normalizer.multiply(value, MATH_CONTEXT)).add(newSimplexTable[i][j], MATH_CONTEXT);
					}
				}
			}
		}

		if (previousSolution.isDual) {
			return new DualTabularSolution(newSimplexTable, newSolutionVariables, previousSolution.objective);
		}
		return new PrimalTabularSolution(newSimplexTable, newSolutionVariables, previousSolution.objective);
	}
}
