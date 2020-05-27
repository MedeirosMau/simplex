package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.List;

import br.helios.simplex.domain.problem.Objective;

public class PrimalTabularSolution extends TabularSolution {

	public PrimalTabularSolution(BigDecimal[][] simplexTable, List<SolutionVariable> variables, Objective objective) {
		super(simplexTable, variables, objective, false);
	}

	@Override
	public SolutionVariable getBasicVariableCandidate(SolutionVariable nonBasicCandidateVariable) {
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

	@Override
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

	@Override
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
}
