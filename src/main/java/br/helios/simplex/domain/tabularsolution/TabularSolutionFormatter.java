package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.infrastructure.io.OutputData.message;
import static java.lang.String.format;

public class TabularSolutionFormatter {

	public static void formatSolution(TabularSolution solution) {
		message(solution).line().log();
	}

	public static void formatBestSolution(TabularSolution solution) {
		message("---------------------------").line().log();
		message("-> BEST SOLUTION FOUND: ").line().log();
		message("---------------------------").line().log();
		message("z = " + solution.getSolutionValue()).line().log();
		for (SolutionVariable variable : solution.variables) {
			message(variable.toString()).line().log();
			message(format("%s = %s ", variable.name(), variable.value(solution.simplexTable))).line().log();
		}
	}
}
