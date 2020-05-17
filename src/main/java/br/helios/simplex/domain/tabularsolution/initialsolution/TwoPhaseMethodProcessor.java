package br.helios.simplex.domain.tabularsolution.initialsolution;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.TabularSolution;
import br.helios.simplex.domain.tabularsolution.TabularSolverService;

class TwoPhaseMethodProcessor implements InitialSolutionProcessor {

	private final CreateFirstPhaseProblemService createFirstPhaseProblemService;
	private final TabularSolverService tabularSolverService;

	public TwoPhaseMethodProcessor() {
		this.createFirstPhaseProblemService = new CreateFirstPhaseProblemService();
		this.tabularSolverService = TabularSolverService.instance();
	}

	@Override
	public TabularSolution createSolution(Problem artificialProblem) {
		Problem firtPhaseProblem = createFirstPhaseProblemService.create(artificialProblem);
		TabularSolution solution = tabularSolverService.solve(firtPhaseProblem);
		return solution;
	}

}
