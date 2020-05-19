package br.helios.simplex.domain.artificialproblem;

import static br.helios.simplex.domain.problem.Objective.INVERTED_MINIMIZATION;
import static br.helios.simplex.domain.problem.Objective.MINIMIZATION;
import static br.helios.simplex.domain.problem.Term.createTermInverted;
import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT;

import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Term;

public class MinimizationProblemConverter {

	public ObjectiveFunction convert(ObjectiveFunction objectiveFunction) {
		if (objectiveFunction.getObjective() == MINIMIZATION) {
			List<Term> newConvertedTerms = new ArrayList<>();
			for (Term term : objectiveFunction.getTerms()) {
				newConvertedTerms.add(createTermInverted(term));
			}
			return new ObjectiveFunction(INVERTED_MINIMIZATION, newConvertedTerms, objectiveFunction.getValue().negate(MATH_CONTEXT));
		}
		throw new IllegalArgumentException("This is not a minimization problem");
	}
}
