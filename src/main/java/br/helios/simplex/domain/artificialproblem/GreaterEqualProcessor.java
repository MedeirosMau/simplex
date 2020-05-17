package br.helios.simplex.domain.artificialproblem;

import static br.helios.simplex.domain.problem.Objective.MAXIMIZATION;
import static br.helios.simplex.domain.problem.Operator.EQUAL;
import static br.helios.simplex.domain.problem.Variable.newArtificialVariable;
import static br.helios.simplex.domain.problem.Variable.newSlackVariable;

import java.util.List;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.Objective;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.Variable;

class GreaterEqualProcessor implements ConstraintProcessor {

	@Override
	public void createConstraint(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint originalConstraint) {
		Constraint newConstraint = new Constraint(originalConstraint, EQUAL);
		createNewSlackVariable(newObjectiveFunction, newConstraints, newConstraint);
		createNewArtificialVariable(newObjectiveFunction, newConstraints, newConstraint);
	}

	private void createNewSlackVariable(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint newConstraint) {
		Variable newSlackVariable = newSlackVariable();
		newObjectiveFunction.addTerm(new Term(0, newSlackVariable));
		newConstraint.addTerm(new Term(-1, newSlackVariable));
		newConstraints.add(newConstraint);
	}

	private void createNewArtificialVariable(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint newConstraint) {
		Variable newArtificialVariable = newArtificialVariable();
		newObjectiveFunction.addTerm(getNewObjectiveFunctionTerm(newObjectiveFunction.getObjective(), newArtificialVariable));
		newConstraint.addTerm(new Term(1, newArtificialVariable));
		newConstraints.add(newConstraint);
	}

	private Term getNewObjectiveFunctionTerm(Objective objective, Variable newArtificialVariable) {
		if (objective == MAXIMIZATION) {
			return new Term(-1, newArtificialVariable);
		} else {
			return new Term(1, newArtificialVariable);
		}
	}
}
