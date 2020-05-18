package br.helios.simplex.domain.problem.variable;

import static br.helios.simplex.domain.problem.variable.VariableType.ARTIFICIAL;
import static br.helios.simplex.domain.problem.variable.VariableType.ORIGINAL;
import static br.helios.simplex.domain.problem.variable.VariableType.SLACK;
import static java.util.Collections.copy;

import java.util.ArrayList;
import java.util.List;

public class Variables {

	private List<Variable> variables = new ArrayList<>();
	private List<Variable> originalVariables = new ArrayList<>();
	private List<Variable> slackVariables = new ArrayList<>();
	private List<Variable> artificialVariables = new ArrayList<>();

	public int newId() {
		return variables.size() + 1;
	}

	public Variable persist(Variable variable) {
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

	public List<Variable> getVariables() {
		return variables;
	}

	public List<Variable> getOriginalVariables() {
		return originalVariables;
	}

	public List<Variable> getSlackVariables() {
		return slackVariables;
	}

	public List<Variable> getArtificialVariables() {
		return artificialVariables;
	}

	public int size() {
		return variables.size();
	}

	public Variable getById(int id) {
		return variables.get(id - 1);
	}

	public Variable getByName(String name) {
		for (Variable variable : variables) {
			if (variable.name.equalsIgnoreCase(name)) {
				return variable;
			}
		}
		return null;
	}

	public Variable[] toArray() {
		return variables.toArray(new Variable[variables.size()]);
	}

	public void clear() {
		variables.clear();
		originalVariables.clear();
		slackVariables.clear();
		artificialVariables.clear();
	}

	@Override
	public Variables clone() {
		Variables clone = new Variables();
		clone.variables = new ArrayList<>(this.variables);
		copy(clone.variables, variables);
		clone.originalVariables = new ArrayList<>(this.originalVariables);
		copy(clone.originalVariables, originalVariables);
		clone.slackVariables = new ArrayList<>(this.slackVariables);
		copy(clone.slackVariables, slackVariables);
		clone.artificialVariables = new ArrayList<>(this.artificialVariables);
		copy(clone.artificialVariables, artificialVariables);
		return clone;
	}
}
