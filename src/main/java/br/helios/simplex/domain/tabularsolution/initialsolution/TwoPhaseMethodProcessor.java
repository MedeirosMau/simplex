package br.helios.simplex.domain.tabularsolution.initialsolution;

import static br.helios.simplex.infrastructure.io.OutputData.message;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.TabularIterationService;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

class TwoPhaseMethodProcessor implements InitialSolutionProcessor {

	private final CreateFirstPhaseProblemService createFirstPhaseProblemService;
	private final TabularIterationService tabularIterationService;
	private final CreateFirstPhaseInitialSolutionService createFirstPhaseInitialSolutionService;
	private final CreateSecondPhaseInitialSolutionService createSecondPhaseInitialSolutionService;

	public TwoPhaseMethodProcessor() {
		this.createFirstPhaseProblemService = new CreateFirstPhaseProblemService();
		this.tabularIterationService = new TabularIterationService();
		this.createFirstPhaseInitialSolutionService = new CreateFirstPhaseInitialSolutionService();
		this.createSecondPhaseInitialSolutionService = new CreateSecondPhaseInitialSolutionService();
	}

	@Override
	public TabularSolution createSolution(Problem artificialProblem) {
		message("@ INITIALIZING TWO PHASE METHOD PROCESSOR\n ").line().log();
		Problem firstPhaseProblem = createFirstPhaseProblemService.create(artificialProblem);
		message("-- FIRST PHASE -- ").log();
		message(firstPhaseProblem.toString()).line().log();
		TabularSolution firstPhaseInitialSolution = createFirstPhaseInitialSolutionService.createSolution(firstPhaseProblem);
		TabularSolution firstPhaseSolution = tabularIterationService.solve(firstPhaseInitialSolution);
		message("\n-- SECOND PHASE --\n").line().log();
		return createSecondPhaseInitialSolutionService.createSolution(artificialProblem, firstPhaseProblem, firstPhaseSolution);
	}

}
