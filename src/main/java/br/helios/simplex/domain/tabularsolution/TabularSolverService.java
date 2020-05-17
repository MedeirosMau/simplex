package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.infrastructure.io.OutputData.message;
import static java.lang.String.format;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.initialsolution.CreateInitialTabularSolutionService;

public class TabularSolverService {

	private static TabularSolverService INSTANCE = new TabularSolverService();

	private final CreateInitialTabularSolutionService createInitialTabularSolutionService;
	private final PivotOperationService pivotOperationService;

	private TabularSolverService() {
		this.createInitialTabularSolutionService = new CreateInitialTabularSolutionService();
		this.pivotOperationService = new PivotOperationService();
	}

	public static TabularSolverService instance() {
		return INSTANCE;
	}

	public TabularSolution solve(Problem artificialProblem) {
		TabularSolution solution = createInitialTabularSolutionService.create(artificialProblem);
		message("-- INITIAL MATRIX TABLE --").line().log();
		message(solution).line().log();

		for (int iteration = 1; !solution.isBestSolution(); iteration++) {
			message("# ITERATION " + iteration).line().log();
			solution = pivotOperationService.pivot(solution);
			message(solution).line().log();
		}

		message("---------------------------").line().log();
		message("-> BEST SOLUTION FOUND: ").line().log();
		message("---------------------------").line().log();
		message("z = " + solution.getSolutionValue()).line().log();
		for (SolutionVariable variable : solution.variables) {
			message(variable.toString()).line().log();
			message(format("%s = %f ", variable.name(), variable.value(solution.simplexTable))).line().log();
		}

		return solution;
	}
}
