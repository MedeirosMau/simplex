package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.infrastructure.io.OutputData.fill;
import static br.helios.simplex.infrastructure.io.OutputData.message;
import static java.lang.String.format;

import br.helios.simplex.infrastructure.util.MathContextUtil;

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
		message("\n\nFINAL TABLEAU:\n").line().log();
		message(solution).line().log();

		message("\nDUAL:\n").line().log();

		int totalNonBasicVariables = solution.simplexTable.length - 1;
		int initialIndex = solution.simplexTable[0].length - totalNonBasicVariables - 1;

		message(fill("Slack") + "\t" + fill("Dual prices")).line().log();

		for (int index = initialIndex; index < solution.variables.size(); index++) {
			SolutionVariable variable = solution.variables.get(index);
			if (variable.isBasic) {
				int i = variable.tableLine;
				message(fill(solution.simplexTable[i][solution.simplexTable[i].length - 1].round(MathContextUtil.MATH_CONTEXT_OUTPUT).toString()))
						.log();
			} else {
				message(fill("0")).log();
			}
			message("\t" + fill(solution.simplexTable[0][index].round(MathContextUtil.MATH_CONTEXT_OUTPUT).toString())).line().log();
		}
	}
}
