package br.helios.simplex.domain.artificialproblem;

import static br.helios.simplex.domain.problem.Operator.EQUAL;
import static br.helios.simplex.domain.problem.Variable.newSlackVariable;

import java.util.List;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.Variable;

class LessEqualProcessor implements ConstraintProcessor {

	@Override
	public void createConstraint(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint originalConstraint) {
		createNewSlackVariable(newObjectiveFunction, newConstraints, originalConstraint);
	}

	private void createNewSlackVariable(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint originalConstraint) {
		Constraint newConstraint = new Constraint(originalConstraint, EQUAL);
		Variable newVariable = newSlackVariable();
		newObjectiveFunction.addTerm(new Term(0, newVariable));
		newConstraint.addTerm(new Term(1, newVariable));
		newConstraints.add(newConstraint);
	}
}
