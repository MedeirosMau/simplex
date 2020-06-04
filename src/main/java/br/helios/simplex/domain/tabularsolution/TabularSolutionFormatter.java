package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.domain.problem.Objective.MAXIMIZATION;
import static br.helios.simplex.domain.problem.Objective.MINIMIZATION;
import static br.helios.simplex.infrastructure.io.OutputData.fill;
import static br.helios.simplex.infrastructure.io.OutputData.message;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.infrastructure.util.MathContextUtil;
import br.helios.simplex.infrastructure.util.MatrixUtil;

public class TabularSolutionFormatter {

	public static void formatSolution(TabularSolution solution) {
		message(solution).line().log();
	}

	public static void formatBestSolution(TabularSolution solution) {
		message("---------------------------").line().log();
		message("    BEST SOLUTION FOUND    ").line().log();
		message("---------------------------").line().log();
		message("\nz = " + solution.getSolutionValue()).log();
		for (SolutionVariable variable : solution.variables) {
			message(format(", %s = %s", variable.name(), variable.value(solution.simplexTable))).log();
		}
		message("\n\n# FINAL TABLEAU:\n").line().log();
		message(solution).line().log();
	}

	public static void formatDual(Problem artificialProblem, TabularSolution solution) {
		message("# DUAL:\n").line().log();

		int constraintsNum = artificialProblem.getConstraints().size();

		// Set matrix C
		BigDecimal[][] matrixC = new BigDecimal[1][constraintsNum];

		List<SolutionVariable> basicVariables = solution.getBasicVariablesOrderedByTableLine();

		for (int j = 0; j < basicVariables.size(); j++) {
			Variable variable = basicVariables.get(j).variable;
			Term term = artificialProblem.getObjectiveFunction().getTermByVariable(variable);
			if (variable.isOriginal) {
				if (artificialProblem.getObjective() == MAXIMIZATION || artificialProblem.getObjective() == MINIMIZATION) {
					matrixC[0][j] = term.getCoefficient();
				} else {
					matrixC[0][j] = term.getCoefficient().negate();
				}

			} else {
				matrixC[0][j] = ZERO;
			}
		}

		// Set matrix A

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

		RealMatrix realMatrixC = MatrixUtil.createRealMatrix(matrixC);
		RealMatrix realInverseMatrixB = MatrixUtil.inverse(matrixB);
		RealMatrix realDualMatrix = realMatrixC.multiply(realInverseMatrixB);
		BigDecimal[][] dual = MatrixUtil.convert(realDualMatrix);

		message(fill("Variable") + "\t\t\t" + fill("Slack") + "\t" + fill("Dual prices")).line().log();
		for (int i = 0; i < basicVariables.size(); i++) {
			SolutionVariable solutionVariable = basicVariables.get(i);
			message(fill(solutionVariable.variable.toString()) + "\t").log();
			if (!solutionVariable.variable.isOriginal) {
				int tableLine = solutionVariable.tableLine;
				BigDecimal value = solution.simplexTable[tableLine][solution.simplexTable[tableLine].length - 1];
				message(fill(value.round(MathContextUtil.MATH_CONTEXT_OUTPUT).toString())).log();
			} else {
				message(fill("0")).log();
			}

			message("\t" + fill(dual[0][i].round(MathContextUtil.MATH_CONTEXT_OUTPUT).toString())).line().log();
		}

//		int totalNonBasicVariables = solution.simplexTable.length - 1;
//		int initialIndex = solution.simplexTable[0].length - totalNonBasicVariables - 1;
//
//		message(fill("Slack") + "\t" + fill("Dual prices")).line().log();
//
//		for (int index = initialIndex; index < solution.variables.size(); index++) {
//			SolutionVariable variable = solution.variables.get(index);
//			if (variable.isBasic) {
//				int i = variable.tableLine;
//				message(fill(solution.simplexTable[i][solution.simplexTable[i].length - 1].round(MathContextUtil.MATH_CONTEXT_OUTPUT).toString()))
//						.log();
//			} else {
//				message(fill("0")).log();
//			}
//			message("\t" + fill(solution.simplexTable[0][index].round(MathContextUtil.MATH_CONTEXT_OUTPUT).toString())).line().log();
//		}
	}
}
