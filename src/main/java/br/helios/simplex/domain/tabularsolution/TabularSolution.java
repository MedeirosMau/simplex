package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.domain.problem.Objective.INVERTED_MINIMIZATION;

import java.util.Arrays;
import java.util.List;

import br.helios.simplex.domain.problem.Objective;

public class TabularSolution {

	public final double[][] simplexTable;
	public final List<SolutionVariable> variables;
	public final Objective objective;

	public TabularSolution(double[][] simplexTable, List<SolutionVariable> variables, Objective objective) {
		this.simplexTable = simplexTable;
		this.variables = variables;
		this.objective = objective;
	}

	public SolutionVariable getBasicVariableCandidate() {
		SolutionVariable candidateVariable = null;

		for (SolutionVariable solutionVariable : variables) {
			if (!solutionVariable.isBasic) {
				double variableCoefficient = simplexTable[0][solutionVariable.index];

				if (variableCoefficient < 0) {
					if (candidateVariable == null) {
						candidateVariable = solutionVariable;
					} else {
						double candidateCoefficient = simplexTable[0][candidateVariable.index];

						if (variableCoefficient < candidateCoefficient) {
							candidateVariable = solutionVariable;
						}
					}
				}
			}
		}

		if (candidateVariable == null) {
			throw new IllegalStateException("No basic candidate was found");
		}

		return candidateVariable;
	}

	public SolutionVariable getNonBasicVariableCandidate(SolutionVariable basicCandidateVariable) {
		SolutionVariable candidateVariable = null;
		double lowestValue = 0;
		int pivotColum = basicCandidateVariable.index;

		for (int i = 1; i < simplexTable.length; i++) {
			SolutionVariable variable = getVariableByTableLine(i);
			if (variable.isBasic) {
				double value = simplexTable[i][simplexTable[i].length - 1] / simplexTable[i][pivotColum];
				if (candidateVariable == null || value < lowestValue) {
					lowestValue = value;
					candidateVariable = variable;
				} else if (value == lowestValue && variable.index < candidateVariable.index) {
					lowestValue = value;
					candidateVariable = variable;
				}
			}
		}

		return candidateVariable;
	}

	public SolutionVariable getVariableByTableLine(int tableLine) {
		for (SolutionVariable variable : variables) {
			if (variable.tableLine == tableLine) {
				return variable;
			}
		}
		throw new IllegalArgumentException("Invalid table line");
	}

	public boolean isBestSolution() {
		for (int j = 0; j < simplexTable[0].length; j++) {
			if (simplexTable[0][j] < 0) {
				return false;
			}
		}
		return true;
	}

	public double[][] copySimplexTable() {
		final double[][] copy = new double[simplexTable.length][];
		for (int i = 0; i < simplexTable.length; i++) {
			copy[i] = Arrays.copyOf(simplexTable[i], simplexTable[i].length);
		}
		return copy;
	}

	public double getSolutionValue() {
		double value = simplexTable[0][simplexTable[0].length - 1];
		if (objective == INVERTED_MINIMIZATION) {
			return -1 * value;
		}
		return value;

	}
}
