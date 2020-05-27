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

class EqualProcessor implements ConstraintProcessor {

	private final CreateVariableService createVariableService;

	public EqualProcessor() {
		this.createVariableService = new CreateVariableService();
	}

	@Override
	public void createConstraint(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint originalConstraint, Variables variables, boolean isDual) {
		createNewArtificialVariable(newObjectiveFunction, newConstraints, originalConstraint, variables, isDual);
	}

	private void createNewArtificialVariable(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint originalConstraint, Variables variables, boolean isDual) {
		Constraint newConstraint = new Constraint(originalConstraint, EQUAL);
		Variable newArtificialVariable = createVariableService.createArtificialVariable(variables, newConstraint, isDual);
		newObjectiveFunction.addTerm(getNewObjectiveFunctionTerm(newObjectiveFunction.getObjective(), newArtificialVariable));
		newConstraint.addTerm(createTerm(ONE, newArtificialVariable));
		newConstraints.add(newConstraint);
	}

	private Term getNewObjectiveFunctionTerm(Objective objective, Variable newArtificialVariable) {
		if (objective == MAXIMIZATION) {
			return createNegativeBigMTerm(newArtificialVariable);
		}
		return createBigMTerm(newArtificialVariable);
	}
}
