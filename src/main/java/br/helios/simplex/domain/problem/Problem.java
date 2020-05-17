package br.helios.simplex.domain.problem;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Problem {

	private final ObjectiveFunction objectiveFunction;
	private final List<Constraint> constraints;

	public Problem(ObjectiveFunction objectiveFunction, List<Constraint> constraints) {
		this.objectiveFunction = objectiveFunction;
		this.constraints = constraints;
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

	public List<Variable> getAllVariables() {
		Set<Variable> allVariables = new LinkedHashSet<Variable>();
		allVariables.addAll(objectiveFunction.getVariables());
		for (Constraint constraint : constraints) {
			allVariables.addAll(constraint.getVariables());
		}
		return new ArrayList<Variable>(allVariables);
	}

	public boolean containsArtificialVariable() {
		for (Variable variable : objectiveFunction.getVariables()) {
			if (variable.isArtificial) {
				return true;
			}
		}
		return false;
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
