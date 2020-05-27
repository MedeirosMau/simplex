package br.helios.simplex.application;

import static br.helios.simplex.infrastructure.io.OutputData.message;

import br.helios.simplex.domain.artificialproblem.CreateArtificialProblemService;
import br.helios.simplex.domain.dualproblem.CreateDualProblemService;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.TabularSolution;
import br.helios.simplex.domain.tabularsolution.TabularSolverService;

public class TabularSolverApplicationService {

	private final CreateArtificialProblemService createArtificialProblemService;
	private final TabularSolverService tabularSolverService;
	private final CreateDualProblemService createDualProblemService;

	public TabularSolverApplicationService() {
		this.createArtificialProblemService = new CreateArtificialProblemService();
		this.tabularSolverService = new TabularSolverService();
		this.createDualProblemService = new CreateDualProblemService();
	}

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

	public TabularSolution solveDual(Problem problem) {
		message("-- ORIGINAL PROBLEM -- ").log();
		message(problem.toString()).line().log();
		Problem dualProblem = createDualProblemService.create(problem);
		message("-- DUAL PROBLEM -- ").log();
		message(dualProblem.toString()).line().log();
		message("-- ARTIFICIAL PROBLEM -- ").log();
		Problem artificialProblem = createArtificialProblemService.create(dualProblem);
		message(artificialProblem.toString()).line().log();
		return tabularSolverService.solve(artificialProblem);
	}

}
