package br.helios.simplex.domain.artificialproblem;

import static br.helios.simplex.domain.problem.Objective.MAXIMIZATION;
import static br.helios.simplex.domain.problem.Operator.EQUAL;
import static br.helios.simplex.domain.problem.Term.createBigMTerm;
import static br.helios.simplex.domain.problem.Term.createNegativeBigMTerm;
import static br.helios.simplex.domain.problem.Term.createTerm;
import static java.math.BigDecimal.ONE;

import java.util.List;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.Objective;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.variable.CreateVariableService;
import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.domain.problem.variable.Variables;
import br.helios.simplex.infrastructure.util.MathContextUtil;

class GreaterEqualProcessor implements ConstraintProcessor {

	private final CreateVariableService createVariableService;
	private final ConstraintConverter constraintConverter;

	public GreaterEqualProcessor() {
		this.createVariableService = new CreateVariableService();
		this.constraintConverter = new ConstraintConverter();
	}

	@Override
	public void createConstraint(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint originalConstraint, Variables variables, boolean isDual) {
		Constraint newConstraint = new Constraint(originalConstraint, EQUAL);
		createNewSlackVariable(newObjectiveFunction, newConstraints, newConstraint, variables, isDual);
		if (!isDual) {
			createNewArtificialVariable(newObjectiveFunction, newConstraints, newConstraint, variables, isDual);
		}
		newConstraints.add(newConstraint);
	}

	private void createNewSlackVariable(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint newConstraint, Variables variables, boolean isDual) {
		Variable newSlackVariable = createVariableService.createSlackVariable(variables, newConstraint, isDual);
		newConstraint.addTerm(createTerm(ONE.negate(MathContextUtil.MATH_CONTEXT), newSlackVariable));
	}

	private void createNewArtificialVariable(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint newConstraint, Variables variables, boolean isDual) {
		Variable newArtificialVariable = createVariableService.createArtificialVariable(variables, newConstraint, isDual);
		newObjectiveFunction.addTerm(getNewObjectiveFunctionTerm(newObjectiveFunction.getObjective(), newArtificialVariable));
		newConstraint.addTerm(createTerm(ONE, newArtificialVariable));
	}

	private Term getNewObjectiveFunctionTerm(Objective objective, Variable newArtificialVariable) {
		if (objective == MAXIMIZATION) {
			return createNegativeBigMTerm(newArtificialVariable);
		}
		return createBigMTerm(newArtificialVariable);
	}
}
