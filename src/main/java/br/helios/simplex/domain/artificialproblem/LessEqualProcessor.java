package br.helios.simplex.domain.artificialproblem;

import static br.helios.simplex.domain.problem.Operator.EQUAL;
import static br.helios.simplex.domain.problem.Term.createTerm;

import java.util.List;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.variable.CreateVariableService;
import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.domain.problem.variable.Variables;

class LessEqualProcessor implements ConstraintProcessor {

	private final CreateVariableService createVariableService;

	public LessEqualProcessor() {
		this.createVariableService = new CreateVariableService();
	}

	@Override
	public void createConstraint(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint originalConstraint, Variables variables) {
		createNewSlackVariable(newObjectiveFunction, newConstraints, originalConstraint, variables);
	}

	private void createNewSlackVariable(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint originalConstraint, Variables variables) {
		Constraint newConstraint = new Constraint(originalConstraint, EQUAL);
		Variable newVariable = createVariableService.createSlackVariable(variables, newConstraint);
		newObjectiveFunction.addTerm(createTerm(0, newVariable));
		newConstraint.addTerm(Term.createTerm(1, newVariable));
		newConstraints.add(newConstraint);
	}
}
