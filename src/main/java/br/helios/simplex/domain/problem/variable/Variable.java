package br.helios.simplex.domain.problem.variable;

import static br.helios.simplex.domain.problem.variable.VariableType.ARTIFICIAL;
import static br.helios.simplex.domain.problem.variable.VariableType.ORIGINAL;
import static br.helios.simplex.infrastructure.util.StringUtil.toShortString;
import static java.lang.String.format;

public class Variable {

	public static final String FAKE_PREFIX = "x";
	public static final String DUAL_PREFIX = "y";
	public static final int UNDEFINED_ID = -1;

	private int id = UNDEFINED_ID;
	public final String name;
	public final VariableType type;
	public final boolean isOriginal;
	public final boolean isArtificial;
	public final int constraintOrder;

	Variable(String name, VariableType type, int constraintOrder) {
		this.name = name.toLowerCase();
		this.type = type;
		this.isOriginal = type == ORIGINAL;
		this.isArtificial = type == ARTIFICIAL;
		this.constraintOrder = constraintOrder;
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
