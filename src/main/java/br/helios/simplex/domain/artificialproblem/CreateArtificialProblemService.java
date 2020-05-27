package br.helios.simplex.domain.artificialproblem;

import static br.helios.simplex.domain.problem.Objective.MINIMIZATION;
import static br.helios.simplex.domain.problem.Operator.EQUAL;
import static br.helios.simplex.domain.problem.Operator.GREATER_EQUAL;
import static br.helios.simplex.domain.problem.Operator.LESS_EQUAL;

import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Operator;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.variable.Variables;

public class CreateArtificialProblemService {

	private final LessEqualProcessor lessEqualProcessor;
	private final EqualProcessor equalProcessor;
	private final GreaterEqualProcessor greaterEqualProcessor;
	private final MinimizationProblemConverter minimizationProblemConverter;
	private final ConstraintConverter negativeConstraintConverter;

	public CreateArtificialProblemService() {
		this.lessEqualProcessor = new LessEqualProcessor();
		this.equalProcessor = new EqualProcessor();
		this.greaterEqualProcessor = new GreaterEqualProcessor();
		this.minimizationProblemConverter = new MinimizationProblemConverter();
		this.negativeConstraintConverter = new ConstraintConverter();
	}

	public Problem create(Problem originalProblem) {
		ObjectiveFunction newObjectiveFunction = new ObjectiveFunction(originalProblem.getObjectiveFunction());
		Variables newVariables = originalProblem.variables.clone();
		List<Constraint> newConstraints = new ArrayList<>();

		for (int i = 0; i < originalProblem.getConstraints().size(); i++) {
			Constraint originalConstraint = originalProblem.getConstraints().get(i);
			if (shouldInvert(originalProblem, originalConstraint)) {
				originalConstraint = negativeConstraintConverter.convert(originalConstraint);
			}
			ConstraintProcessor constraintProcessor = getConstraintProcessor(originalConstraint.getOperator());
			constraintProcessor.createConstraint(newObjectiveFunction, newConstraints, originalConstraint, newVariables, false);
		}

		if (originalProblem.getObjective() == MINIMIZATION) {
			newObjectiveFunction = minimizationProblemConverter.convert(newObjectiveFunction);
		}

		return new Problem(newObjectiveFunction, newConstraints, newVariables, originalProblem, false);
	}

	private boolean shouldInvert(Problem originalProblem, Constraint originalConstraint) {
		// return (!originalProblem.isDual &&
		// originalConstraint.getConstraintValue().signum() < 0)
		// || (originalProblem.isDual &&
		// originalConstraint.getConstraintValue().signum() >= 0);
		return (!originalProblem.isDual && originalConstraint.getConstraintValue().signum() < 0);
	}

	private ConstraintProcessor getConstraintProcessor(Operator constraintOperator) {
		if (constraintOperator == LESS_EQUAL) {
			return lessEqualProcessor;
		} else if (constraintOperator == EQUAL) {
			return equalProcessor;
		} else if (constraintOperator == GREATER_EQUAL) {
			return greaterEqualProcessor;
		} else {
			throw new IllegalArgumentException("Invalid operator.");
		}
	}

}
