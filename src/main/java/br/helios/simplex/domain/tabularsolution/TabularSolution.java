package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.domain.problem.Objective.INVERTED_MINIMIZATION;
import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import br.helios.simplex.domain.problem.Objective;
import br.helios.simplex.infrastructure.util.MathContextUtil;

public class TabularSolution {

	public final BigDecimal[][] simplexTable;
	public final List<SolutionVariable> variables;
	public final Objective objective;
	public boolean foundBestSolution;

	public TabularSolution(BigDecimal[][] simplexTable, List<SolutionVariable> variables, Objective objective) {
		this.simplexTable = simplexTable;
		this.variables = variables;
		this.objective = objective;
	}

	public SolutionVariable getBasicVariableCandidate() {
		SolutionVariable candidateVariable = null;

		for (SolutionVariable solutionVariable : variables) {
			if (!solutionVariable.isBasic) {
				BigDecimal variableCoefficient = simplexTable[0][solutionVariable.index];

				if (variableCoefficient.compareTo(ZERO) < 0) {
					if (candidateVariable == null) {
						candidateVariable = solutionVariable;
					} else {
						BigDecimal candidateCoefficient = simplexTable[0][candidateVariable.index];

						if (variableCoefficient.compareTo(candidateCoefficient) < 0) {
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
		BigDecimal lowestValue = ZERO;
		int pivotColum = basicCandidateVariable.index;

		for (int i = 1; i < simplexTable.length; i++) {
			SolutionVariable variable = getVariableByTableLine(i);
			if (variable.isBasic && simplexTable[i][pivotColum].compareTo(ZERO) > 0) {
				BigDecimal value = simplexTable[i][simplexTable[i].length - 1].divide(simplexTable[i][pivotColum], MATH_CONTEXT);
				if (candidateVariable == null || value.compareTo(lowestValue) < 0) {
					lowestValue = value;
					candidateVariable = variable;
				} else if (value.compareTo(lowestValue) == 0 && variable.index < candidateVariable.index) {
					lowestValue = value;
					candidateVariable = variable;
				}
			}
		}

		this.foundBestSolution = true;

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
		if (foundBestSolution) {
			return true;
		}
		for (int j = 0; j < simplexTable[0].length - 1; j++) {
			if (simplexTable[0][j].compareTo(ZERO) < 0) {
				return false;
			}
		}
		return true;
	}

	public BigDecimal[][] copySimplexTable() {
		final BigDecimal[][] copy = new BigDecimal[simplexTable.length][];
		for (int i = 0; i < simplexTable.length; i++) {
			copy[i] = Arrays.copyOf(simplexTable[i], simplexTable[i].length);
		}
		return copy;
	}

	public BigDecimal getSolutionValue() {
		BigDecimal value = simplexTable[0][simplexTable[0].length - 1];
		if (objective == INVERTED_MINIMIZATION) {
			return value.negate(MathContextUtil.MATH_CONTEXT);
		}
		return value;

	}
}
