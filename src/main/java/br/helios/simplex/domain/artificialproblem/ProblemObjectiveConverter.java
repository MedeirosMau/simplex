package br.helios.simplex.domain.artificialproblem;

import static br.helios.simplex.domain.problem.Objective.INVERTED_MAXIMIZATION;
import static br.helios.simplex.domain.problem.Objective.INVERTED_MINIMIZATION;
import static br.helios.simplex.domain.problem.Objective.MAXIMIZATION;
import static br.helios.simplex.domain.problem.Objective.MINIMIZATION;
import static br.helios.simplex.domain.problem.Term.createTermInverted;
import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT;

import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.Objective;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Term;

public class ProblemObjectiveConverter {

	public ObjectiveFunction convert(ObjectiveFunction objectiveFunction) {
		List<Term> newConvertedTerms = new ArrayList<>();
		for (Term term : objectiveFunction.getTerms()) {
			newConvertedTerms.add(createTermInverted(term));
		}
		return new ObjectiveFunction(newObjective(objectiveFunction.getObjective()), newConvertedTerms,
				objectiveFunction.getValue().negate(MATH_CONTEXT));
	}

	private Objective newObjective(Objective originalObjective) {
		switch (originalObjective) {
		case MINIMIZATION:
			return INVERTED_MINIMIZATION;
		case MAXIMIZATION:
			return INVERTED_MAXIMIZATION;
		case INVERTED_MINIMIZATION:
			return MINIMIZATION;
		case INVERTED_MAXIMIZATION:
			return MAXIMIZATION;
		}
		throw new IllegalArgumentException("Not a valid objective");
	}
}
