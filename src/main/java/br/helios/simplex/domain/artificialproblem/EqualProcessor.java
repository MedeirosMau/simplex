package br.helios.simplex.domain.artificialproblem;

import static br.helios.simplex.domain.problem.Objective.MAXIMIZATION;
import static br.helios.simplex.domain.problem.Operator.EQUAL;
import static br.helios.simplex.domain.problem.Variable.newArtificialVariable;

import java.util.List;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.Objective;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.Variable;

class EqualProcessor implements ConstraintProcessor {

	@Override
	public void createConstraint(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint originalConstraint) {
		createNewArtificialVariable(newObjectiveFunction, newConstraints, originalConstraint);
	}

	private void createNewArtificialVariable(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint originalConstraint) {
		Constraint newConstraint = new Constraint(originalConstraint, EQUAL);
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
