package br.helios.simplex.domain.problem;

import static br.helios.simplex.infrastructure.util.MatrixUtil.convert;
import static br.helios.simplex.infrastructure.util.MatrixUtil.inverse;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.List;

import br.helios.simplex.domain.tabularsolution.SolutionVariable;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

public class BMatrix {

	public static BigDecimal[][] create(TabularSolution tabularSolution, Problem artificialProblem) {
		int constraintsNum = artificialProblem.getConstraints().size();
		List<SolutionVariable> basicVariables = tabularSolution.getBasicVariables();

		BigDecimal[][] matrixB = new BigDecimal[constraintsNum][constraintsNum];

		for (int i = 0; i < constraintsNum; i++) {
			Constraint constraint = artificialProblem.getConstraints().get(i);
			for (int j = 0; j < basicVariables.size(); j++) {
				SolutionVariable basicVariable = basicVariables.get(j);
				Term term = constraint.getTermByVariable(basicVariable.variable);
				if (term != null) {
					matrixB[i][j] = term.getCoefficient();
				} else {
					matrixB[i][j] = ZERO;
				}
			}
		}

		return matrixB;
	}

	public static BigDecimal[][] createNormalized(TabularSolution tabularSolution, Problem artificialProblem) {
		BigDecimal[][] inverseBMatrix = convert(inverse(create(tabularSolution, artificialProblem)));
		BigDecimal[][] normalizedMatrix = new BigDecimal[inverseBMatrix.length][inverseBMatrix[0].length];

		for (int i = 0; i < tabularSolution.getBasicVariables().size(); i++) {
			SolutionVariable solutionVariable = tabularSolution.getBasicVariables().get(i);
			for (int j = 0; j < inverseBMatrix[0].length; j++) {
				normalizedMatrix[solutionVariable.tableLine - 1][j] = inverseBMatrix[i][j];
			}
		}

		return normalizedMatrix;
	}
}
