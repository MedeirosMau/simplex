package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.domain.tabularsolution.TabularSolutionFormatter.formatBestSolution;
import static br.helios.simplex.domain.tabularsolution.TabularSolutionFormatter.formatSolution;
import static br.helios.simplex.infrastructure.io.OutputData.message;

public class TabularIterationService {

	private final PivotOperationService pivotOperationService;

	public TabularIterationService() {
		this.pivotOperationService = new PivotOperationService();
	}

	public TabularSolution solve(TabularSolution solution) {
		message("-- INITIAL MATRIX TABLE --").line().log();
		formatSolution(solution);
		for (int iteration = 1; !solution.isBestSolution(); iteration++) {
			message("# ITERATION " + iteration).line().log();
			solution = pivotOperationService.pivot(solution);
			formatSolution(solution);
		}
		formatBestSolution(solution);
		return solution;
	}

}
