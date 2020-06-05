package br.helios.simplex.domain.dualproblem;

import static br.helios.simplex.infrastructure.io.OutputData.fill;
import static br.helios.simplex.infrastructure.io.OutputData.message;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
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

		List<SolutionVariable> basicVariables = solution.getBasicVariablesOrderedByTableLine();

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
		for (int i = 0; i < basicVariables.size(); i++) {
			SolutionVariable solutionVariable = basicVariables.get(i);
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
