package br.helios.simplex.domain.artificialproblem;

import java.util.List;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.variable.Variables;

public interface ConstraintProcessor {

	void createConstraint(ObjectiveFunction newObjectiveFunction, List<Constraint> newConstraints, Constraint originalConstraint, Variables variables);
}
