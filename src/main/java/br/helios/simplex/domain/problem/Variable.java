package br.helios.simplex.domain.problem;

import static br.helios.simplex.domain.problem.VariableType.ARTIFICIAL;
import static br.helios.simplex.domain.problem.VariableType.ORIGINAL;
import static br.helios.simplex.domain.problem.VariableType.SLACK;
import static br.helios.simplex.domain.problem.Variables.getByName;
import static br.helios.simplex.domain.problem.Variables.persist;
import static br.helios.simplex.infrastructure.util.StringUtil.toShortString;
import static java.lang.String.format;

public class Variable {

	public static final String FAKE_PREFIX = "x";
	public static final int UNDEFINED_ID = -1;

	private int id = UNDEFINED_ID;
	public final String name;
	public final VariableType type;
	public final boolean isOriginal;
	public final boolean isArtificial;

	private Variable(String name, VariableType type) {
		this.name = name.toLowerCase();
		this.type = type;
		this.isOriginal = type == ORIGINAL;
		this.isArtificial = type == ARTIFICIAL;
	}

	public static Variable newOriginalVariable(String variableName) {
		Variable variable = getByName(variableName);
		if (variable != null) {
			return variable;
		}
		return persist(new Variable(variableName, ORIGINAL));
	}

	public static Variable newSlackVariable() {
		int total = Variables.size();
		return persist(new Variable(FAKE_PREFIX + (total + 1), SLACK));
	}

	public static Variable newArtificialVariable() {
		int total = Variables.size();
		return persist(new Variable(FAKE_PREFIX + (total + 1), ARTIFICIAL));
	}

	void saveId(int id) {
		this.id = id;
	}

	public int id() {
		return id;
	}

	public boolean isUndefined() {
		return this.id == UNDEFINED_ID;
	}

	@Override
	public String toString() {
		return format("Variable %s (id: %d, original: %s)", name, id, toShortString(isOriginal));
	}
}
