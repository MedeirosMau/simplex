package br.helios.simplex.domain.tabularsolution.initialsolution;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

public class CreateInitialTabularSolutionService {

	private final SimpleProcessor simpleProcessor;
	private final TwoPhaseMethodProcessor twoPhaseMethodProcessor;

	public CreateInitialTabularSolutionService() {
		this.simpleProcessor = new SimpleProcessor();
		this.twoPhaseMethodProcessor = new TwoPhaseMethodProcessor();
	}

	public TabularSolution create(Problem artificialProblem) {
		InitialSolutionProcessor processor = getProcessor(artificialProblem);
		return processor.createSolution(artificialProblem);
	}

	private InitialSolutionProcessor getProcessor(Problem artificialProblem) {
		if (artificialProblem.hasBigMTerm()) {
			return twoPhaseMethodProcessor;
		}
		return simpleProcessor;
	}
}
