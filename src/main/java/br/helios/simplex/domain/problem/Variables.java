package br.helios.simplex.domain.problem;

import static br.helios.simplex.domain.problem.VariableType.ARTIFICIAL;
import static br.helios.simplex.domain.problem.VariableType.ORIGINAL;
import static br.helios.simplex.domain.problem.VariableType.SLACK;

import java.util.ArrayList;
import java.util.List;

public class Variables {

	private static List<Variable> variables = new ArrayList<>();
	private static List<Variable> originalVariables = new ArrayList<>();
	private static List<Variable> slackVariables = new ArrayList<>();
	private static List<Variable> artificialVariables = new ArrayList<>();

	public static int newId() {
		return variables.size() + 1;
	}

	public static Variable persist(Variable variable) {
		if (variable.isUndefined()) {
			variable.saveId(newId());
			variables.add(variable);
			if (variable.type == ORIGINAL) {
				originalVariables.add(variable);
			} else if (variable.type == SLACK) {
				slackVariables.add(variable);
			} else if (variable.type == ARTIFICIAL) {
				artificialVariables.add(variable);
			}
			return variable;
		}
		throw new IllegalStateException("Already a persisted variable: " + variable.toString());
	}

	public static List<Variable> getVariables() {
		return variables;
	}

	public static List<Variable> getOriginalVariables() {
		return originalVariables;
	}

	public static List<Variable> getSlackVariables() {
		return slackVariables;
	}

	public static List<Variable> getArtificialVariables() {
		return artificialVariables;
	}

	public static int size() {
		return variables.size();
	}

	public static Variable getById(int id) {
		return variables.get(id - 1);
	}

	public static Variable getByName(String name) {
		for (Variable variable : variables) {
			if (variable.name.equalsIgnoreCase(name)) {
				return variable;
			}
		}
		return null;
	}

	public static Variable[] toArray() {
		return variables.toArray(new Variable[variables.size()]);
	}

	public static void clear() {
		variables.clear();
		originalVariables.clear();
		slackVariables.clear();
	}
}
