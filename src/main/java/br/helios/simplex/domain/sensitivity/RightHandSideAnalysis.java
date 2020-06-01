package br.helios.simplex.domain.sensitivity;

import static br.helios.simplex.infrastructure.io.OutputData.INFINITY;
import static br.helios.simplex.infrastructure.io.OutputData.fill;
import static br.helios.simplex.infrastructure.io.OutputData.message;
import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT;
import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT_OUTPUT;
import static java.lang.String.format;

import java.math.BigDecimal;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.SolutionVariable;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

class RightHandSideAnalysis {

	public void analyse(TabularSolution tabularSolution, Problem problem) {
		message("\n# RIGHTHAND SIDE RANGES\n").line().log();

		BigDecimal[][] simplexTable = tabularSolution.simplexTable;

		message(format("%s\t%s\t%s\t%s", fill("ROW"), fill("CURRENT RHS"), fill("ALLOWABLE INCREASE"), fill("ALLOWABLE DECREASE"))).line().log();

		for (int index = 1; index < simplexTable.length; index++) {

			// For every b value

			BigDecimal lowerBound = null;
			BigDecimal upperBound = null;

			SolutionVariable constraintVariable = tabularSolution.getNonOriginalVariableFromConstraint(index);
			if (constraintVariable.variable.isArtificial) {
				continue;
			}
			// message(constraintVariable.toString()).line().log();

			BigDecimal currentRightHandSize = problem.getConstraints().get(index - 1).getConstraintValue();

			for (int i = 1; i < simplexTable.length; i++) {
				BigDecimal rightHandSideValue = simplexTable[i][simplexTable[i].length - 1];
				// message("right hand side value: " +
				// rightHandSideValue.toString()).line().log();
				BigDecimal columnValue = simplexTable[i][constraintVariable.index];
				// message("columnValue: " + columnValue.toString()).line().log();
				BigDecimal bound = null;

				if (columnValue.signum() != 0) {
					bound = (rightHandSideValue.negate()).divide(columnValue, MATH_CONTEXT);
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

//				if ((rightHandSideValue.signum() >= 0 && columnValue.signum() >= 0)
//						|| (rightHandSideValue.signum() <= 0 && columnValue.signum() <= 0)) {
//					if (lowerBound == null || bound.compareTo(lowerBound) > 0) {
//						lowerBound = bound;
//					}
//				} else {
//					if (upperBound == null || bound.compareTo(upperBound) < 0) {
//						upperBound = bound;
//					}
//				}
			}

			message(format("%s\t%s\t%s\t%s", fill(String.valueOf(index + 1)), fill(currentRightHandSize.toString()), formatBound(upperBound),
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
