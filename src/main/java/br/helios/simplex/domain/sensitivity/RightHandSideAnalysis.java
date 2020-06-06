package br.helios.simplex.domain.sensitivity;

import static br.helios.simplex.infrastructure.io.OutputData.INFINITY;
import static br.helios.simplex.infrastructure.io.OutputData.fill;
import static br.helios.simplex.infrastructure.io.OutputData.message;
import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT;
import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT_OUTPUT;
import static java.lang.String.format;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.helios.simplex.domain.problem.BMatrix;
import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

class RightHandSideAnalysis {

	public void analyse(TabularSolution tabularSolution, Problem artificialProblem) {
		message("\n# RIGHTHAND SIDE RANGES\n").line().log();

		BigDecimal[][] simplexTable = tabularSolution.simplexTable;
		BigDecimal[][] inverseBMatrix = BMatrix.createNormalized(tabularSolution, artificialProblem);

		message(format("%s\t%s\t%s\t%s", fill("ROW"), fill("CURRENT RHS"), fill("ALLOWABLE INCREASE"), fill("ALLOWABLE DECREASE"))).line().log();

		// for (int index = 1; index < simplexTable.length; index++) {
		for (int j = 0; j < inverseBMatrix[0].length; j++) {

			Constraint constraint = artificialProblem.getConstraints().get(j);
			BigDecimal currentRightHandSize = constraint.getConstraintValue();

			// For every b value

			BigDecimal lowerBound = null;
			BigDecimal upperBound = null;

			for (int i = 0; i < inverseBMatrix.length; i++) {

				// TODO esta lógica de pegar os ultimos esta errada!

				BigDecimal rightHandSideValue = simplexTable[i + 1][simplexTable[i + 1].length - 1];

				// message("right hand side value: " + rightHandSideValue.toString()).log();
				BigDecimal columnValue = inverseBMatrix[i][j];
				columnValue = columnValue.setScale(5, RoundingMode.HALF_UP);
				// message(", columnValue: " + columnValue.toString()).line().log();
				BigDecimal bound = null;

				if (columnValue.signum() != 0) {
					bound = (rightHandSideValue.negate()).divide(columnValue, MATH_CONTEXT);
					// message(", bound: " + bound.toString()).line().log();
				} else {
					continue;
				}

				if (columnValue.signum() >= 0) {
					if (lowerBound == null || bound.compareTo(lowerBound) > 0) {
						lowerBound = bound;
					}
				} else {
					if (upperBound == null || bound.compareTo(upperBound) < 0) {
						upperBound = bound;
					}
				}
			}

			message(format("%s\t%s\t%s\t%s", fill(String.valueOf(j + 1)), fill(currentRightHandSize.toString()), formatBound(upperBound),
					formatBound(lowerBound))).line().log();
		}
	}

	private String formatBound(BigDecimal bound) {
		String output = INFINITY;
		if (bound != null) {
			output = bound.abs().round(MATH_CONTEXT_OUTPUT).toString();
		}
		return fill(output);
	}
}
