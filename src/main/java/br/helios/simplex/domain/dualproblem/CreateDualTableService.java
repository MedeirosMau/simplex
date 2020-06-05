package br.helios.simplex.domain.dualproblem;

import static br.helios.simplex.infrastructure.io.OutputData.fill;
import static br.helios.simplex.infrastructure.io.OutputData.message;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.domain.tabularsolution.SolutionVariable;
import br.helios.simplex.domain.tabularsolution.TabularSolution;
import br.helios.simplex.infrastructure.util.MathContextUtil;
import br.helios.simplex.infrastructure.util.MatrixUtil;

public class CreateDualTableService {

	public DualDataTable createDualTable(TabularSolution solution, Problem artificialProblem) {
		DualDataTable dualTable = new DualDataTable();
		message("# DUAL:\n").line().log();

		int constraintsNum = artificialProblem.getConstraints().size();

		// Set matrix C
		BigDecimal[][] matrixC = new BigDecimal[1][constraintsNum];

		List<SolutionVariable> basicVariables = solution.getBasicVariables();

		for (int j = 0; j < basicVariables.size(); j++) {
			Variable variable = basicVariables.get(j).variable;
			Term term = artificialProblem.getObjectiveFunction().getTermByVariable(variable);
			if (variable.isOriginal) {
				matrixC[0][j] = term.getCoefficient();

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

		List<SolutionVariable> orderedBasicVariables = solution.getBasicVariablesOrderedByTableLine();

		SolutionVariable[] variables = new SolutionVariable[basicVariables.size()];

//		for (int index = 0; index < basicVariables.size(); index++) {
//			SolutionVariable solutionVariable = basicVariables.get(index);
//			boolean flag = false;
//
//			for (int i = 0; i < artificialProblem.getConstraints().size(); i++) {
//				Constraint constraint = artificialProblem.getConstraints().get(i);
//
//				if (!solutionVariable.variable.isOriginal) {
//					if (constraint.getTermByVariable(solutionVariable.variable) != null) {
//						variables[i] = solutionVariable;
//						flag = true;
//						break;
//					}
//				}
//			}
//		}

		List<SolutionVariable> ignored = new ArrayList<>();

		for (int i = 0; i < artificialProblem.getConstraints().size(); i++) {
			Constraint constraint = artificialProblem.getConstraints().get(i);

			for (int index = 0; index < basicVariables.size(); index++) {
				SolutionVariable solutionVariable = basicVariables.get(index);

				if (ignored.contains(solutionVariable)) {
					continue;
				}

				if (!solutionVariable.variable.isOriginal) {
					if (constraint.getTermByVariable(solutionVariable.variable) != null) {
						variables[i] = solutionVariable;
						basicVariables.remove(index);
						// ignored.add(solutionVariable);
						break;
					}
				}

				if (index == basicVariables.size() - 1 && variables[i] == null) {
					variables[i] = basicVariables.get(0);
					basicVariables.remove(0);
					// ignored.add(basicVariables.get(i));
				}
			}

		}

		for (int i = 0; i < variables.length; i++) {
			SolutionVariable solutionVariable = variables[i];
			message(fill(solutionVariable.variable.toString()) + "\t").log();

			BigDecimal slackValue = BigDecimal.ZERO;

			if (!solutionVariable.variable.isOriginal) {
				int tableLine = solutionVariable.tableLine;
				slackValue = solution.simplexTable[tableLine][solution.simplexTable[tableLine].length - 1];
			}

			message(fill(slackValue.round(MathContextUtil.MATH_CONTEXT_OUTPUT).toString())).log();

			BigDecimal dualValue = dual[0][i].round(MathContextUtil.MATH_CONTEXT_OUTPUT);
			message("\t" + fill(dualValue.toString())).line().log();
			dualTable.add(new DualData(solutionVariable, slackValue, dualValue));
		}

		return dualTable;
	}
}
