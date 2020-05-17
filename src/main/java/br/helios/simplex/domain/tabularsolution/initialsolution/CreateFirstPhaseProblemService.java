package br.helios.simplex.domain.tabularsolution.initialsolution;

import static br.helios.simplex.domain.problem.Objective.MINIMIZATION;
import static br.helios.simplex.domain.problem.Variables.getArtificialVariables;

import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.Variable;

class CreateFirstPhaseProblemService {

	public Problem create(Problem artificialProblem) {
		ObjectiveFunction newObjectiveFunction = new ObjectiveFunction(MINIMIZATION, createNewTerms());
		return new Problem(newObjectiveFunction, artificialProblem.getConstraints());
	}

	private List<Term> createNewTerms() {
		List<Term> terms = new ArrayList<>();
		for (Variable variable : getArtificialVariables()) {
			terms.add(new Term(1d, variable));
		}
		return terms;
	}
}
