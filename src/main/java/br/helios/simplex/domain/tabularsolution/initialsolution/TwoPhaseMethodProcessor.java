package br.helios.simplex.domain.tabularsolution.initialsolution;

import static br.helios.simplex.infrastructure.io.OutputData.message;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.TabularSolution;
import br.helios.simplex.domain.tabularsolution.TabularSolverService;

class TwoPhaseMethodProcessor implements InitialSolutionProcessor {

	private final CreateFirstPhaseProblemService createFirstPhaseProblemService;
	private final InitialSimplexTableFactory initialSimplexTableFactory;
	private final TabularSolverService tabularSolverService;
	private final CreateFirstPhaseInitialSolutionService createFirstPhaseInitialSolutionService;

	public TwoPhaseMethodProcessor() {
		this.createFirstPhaseProblemService = new CreateFirstPhaseProblemService();
		this.tabularSolverService = TabularSolverService.instance();
		this.initialSimplexTableFactory = new InitialSimplexTableFactory();
		this.createFirstPhaseInitialSolutionService = new CreateFirstPhaseInitialSolutionService();
	}

	@Override
	public TabularSolution createSolution(Problem artificialProblem) {
		message("@ INITIALIZING TWO PHASE METHOD PROCESSOR  ").line().log();
		Problem firstPhaseProblem = createFirstPhaseProblemService.create(artificialProblem);
		message("-- FIRST PHASE PROBLEM -- ").log();
		message(firstPhaseProblem.toString()).line().log();
		TabularSolution firstPhaseTabularSolution = createFirstPhaseInitialSolutionService.createSolution(firstPhaseProblem);
		message(firstPhaseTabularSolution).line().log();
		// System.exit(0);
		return firstPhaseTabularSolution;
	}

}
