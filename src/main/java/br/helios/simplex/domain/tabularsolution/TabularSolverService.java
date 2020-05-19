package br.helios.simplex.domain.tabularsolution;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.initialsolution.CreateInitialTabularSolutionService;

public class TabularSolverService {

	private final CreateInitialTabularSolutionService createInitialTabularSolutionService;
	private final TabularIterationService tabularIterationService;

	public TabularSolverService() {
		this.createInitialTabularSolutionService = new CreateInitialTabularSolutionService();
		this.tabularIterationService = new TabularIterationService();
	}

	public TabularSolution solve(Problem artificialProblem) {
		return tabularIterationService.solve(createInitialTabularSolutionService.create(artificialProblem));
	}

}
