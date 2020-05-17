package br.helios.simplex.domain.tabularsolution.initialsolution;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

public interface InitialSolutionProcessor {

	TabularSolution createSolution(Problem artificialProblem);
}
