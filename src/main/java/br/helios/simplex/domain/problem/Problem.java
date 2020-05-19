package br.helios.simplex.domain.problem;

import java.util.List;

import br.helios.simplex.domain.problem.variable.Variables;

public class Problem {

	private final ObjectiveFunction objectiveFunction;
	private final List<Constraint> constraints;
	public final Variables variables;
	public final Problem origin;

	public Problem(ObjectiveFunction objectiveFunction, List<Constraint> constraints, Variables variables, Problem origin) {
		this.objectiveFunction = objectiveFunction;
		this.constraints = constraints;
		this.variables = variables;
		this.origin = origin;
	}

	public ObjectiveFunction getObjectiveFunction() {
		return objectiveFunction;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public Objective getObjective() {
		return objectiveFunction.getObjective();
	}

	public boolean hasBigMTerm() {
		return objectiveFunction.hasBigMTerm();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("\n");
		builder.append("Objective function: " + objectiveFunction.toString() + "\n");
		builder.append("Constraints:\n");
		for (Constraint constraint : constraints) {
			builder.append(constraint.toString() + "\n");
		}
		return builder.toString();
	}
}
