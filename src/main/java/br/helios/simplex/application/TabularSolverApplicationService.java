package br.helios.simplex.application;

import static br.helios.simplex.infrastructure.io.OutputData.message;

import br.helios.simplex.domain.artificialproblem.CreateArtificialProblemService;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.TabularSolution;
import br.helios.simplex.domain.tabularsolution.TabularSolverService;

public class TabularSolverApplicationService {

	private final CreateArtificialProblemService createArtificialProblemService;
	private final TabularSolverService tabularSolverService;

	public TabularSolverApplicationService() {
		this.createArtificialProblemService = new CreateArtificialProblemService();
		this.tabularSolverService = TabularSolverService.instance();
	}

	// TODO: equality constraint
	// TODO: greater than constraint
	// TODO: two phase method
	// TODO: minimization problem
	// TODO: negative constraint
	// TODO: negative variables
	// TODO: dual
	// TODO: sensitivity analysis
	public TabularSolution solve(Problem problem) {
		message("-- ORIGINAL PROBLEM -- ").log();
		message(problem.toString()).line().log();
		Problem artificialProblem = createArtificialProblemService.create(problem);
		message("-- ARTIFICIAL PROBLEM -- ").log();
		message(artificialProblem.toString()).line().log();
		return tabularSolverService.solve(artificialProblem);
	}
}
