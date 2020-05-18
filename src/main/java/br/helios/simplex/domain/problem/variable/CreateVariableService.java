package br.helios.simplex.domain.problem.variable;

import static br.helios.simplex.domain.problem.variable.Variable.FAKE_PREFIX;
import static br.helios.simplex.domain.problem.variable.VariableType.ARTIFICIAL;
import static br.helios.simplex.domain.problem.variable.VariableType.ORIGINAL;
import static br.helios.simplex.domain.problem.variable.VariableType.SLACK;

import br.helios.simplex.domain.problem.Constraint;

public class CreateVariableService {

	public Variable createOriginalVariable(String variableName, Variables variables) {
		Variable variable = variables.getByName(variableName);
		if (variable != null) {
			return variable;
		}
		return variables.persist(new Variable(variableName, ORIGINAL, 0));
	}

	public Variable createSlackVariable(Variables variables, Constraint constraint) {
		int total = variables.size();
		return variables.persist(new Variable(FAKE_PREFIX + (total + 1), SLACK, constraint.order));
	}

	public Variable createArtificialVariable(Variables variables, Constraint constraint) {
		int total = variables.size();
		return variables.persist(new Variable(FAKE_PREFIX + (total + 1), ARTIFICIAL, constraint.order));
	}
}
