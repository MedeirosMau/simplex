package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.List;

import br.helios.simplex.domain.problem.Objective;
import br.helios.simplex.domain.problem.Problem;

public class DualTabularSolution extends TabularSolution {

	public DualTabularSolution(BigDecimal[][] simplexTable, List<SolutionVariable> variables, Objective objective, Problem problem) {
		super(simplexTable, variables, objective, problem);
	}

	@Override
	public SolutionVariable getBasicVariableCandidate(SolutionVariable nonBasicCandidateVariable) {

		// Identify lowest ratio between negative non basic variables

		SolutionVariable candidateVariable = null;
		BigDecimal lowestValue = ZERO;
		int pivotLine = nonBasicCandidateVariable.tableLine;

		for (int j = 0; j < simplexTable[pivotLine].length - 1; j++) {
			SolutionVariable variable = getVariableWithIndex(j);
			if (!variable.isBasic && simplexTable[pivotLine][j].signum() < 0) {
				BigDecimal value = (simplexTable[0][j].divide(simplexTable[pivotLine][j], MATH_CONTEXT)).abs();
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

	@Override
	public SolutionVariable getNonBasicVariableCandidate(SolutionVariable basicCandidateVariable) {

		// Negative basic variable with highest absolute value

		SolutionVariable candidateVariable = null;

		for (SolutionVariable solutionVariable : variables) {
			if (solutionVariable.isBasic) {
				BigDecimal variableValue = solutionVariable.value(this.simplexTable);

				if (variableValue.compareTo(ZERO) < 0) {
					if (candidateVariable == null) {
						candidateVariable = solutionVariable;
					} else {
						BigDecimal candidateValue = candidateVariable.value(this.simplexTable);

						if (variableValue.compareTo(candidateValue) < 0) {
							candidateVariable = solutionVariable;
						}
					}
				}
			}
		}

		if (candidateVariable == null) {
			throw new IllegalStateException("No non basic candidate was found");
		}

		return candidateVariable;
	}

	@Override
	public boolean isBestSolution() {

		if (foundBestSolution) {
			return true;
		}

		// True when all basic variables are non-negative

		for (SolutionVariable solutionVariable : variables) {
			if (solutionVariable.isBasic && solutionVariable.value(this.simplexTable).signum() < 0) {
				return false;
			}
		}
		return true;
	}

}
