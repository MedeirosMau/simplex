package br.helios.simplex.domain.artificialproblem;

import static br.helios.simplex.domain.problem.Objective.INVERTED_MINIMIZATION;
import static br.helios.simplex.domain.problem.Objective.MINIMIZATION;

import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Term;

public class MinimizationProblemConverter {

	public ObjectiveFunction convert(ObjectiveFunction objectiveFunction) {
		if (objectiveFunction.getObjective() == MINIMIZATION) {
			List<Term> newConvertedTerms = new ArrayList<>();
			for (Term term : objectiveFunction.getTerms()) {
				newConvertedTerms.add(convertTermForMinimization(term));
			}
			return new ObjectiveFunction(INVERTED_MINIMIZATION, newConvertedTerms);
		}
		throw new IllegalArgumentException("This is not a minimization problem");
	}

	public Term convertTermForMinimization(Term originalTerm) {
		return new Term(-1 * originalTerm.getCoefficient(), originalTerm.getVariable());
	}
}
