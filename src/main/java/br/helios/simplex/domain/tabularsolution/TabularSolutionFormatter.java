package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.infrastructure.io.OutputData.message;
import static java.lang.String.format;

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
}
