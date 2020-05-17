package br.helios.simplex.domain.problem;

import static br.helios.simplex.domain.problem.Variable.UNDEFINED_ID;
import static br.helios.simplex.domain.problem.Variable.newArtificialVariable;
import static br.helios.simplex.domain.problem.Variable.newOriginalVariable;
import static br.helios.simplex.domain.problem.Variable.newSlackVariable;
import static br.helios.simplex.domain.problem.VariableType.ARTIFICIAL;
import static br.helios.simplex.domain.problem.VariableType.ORIGINAL;
import static br.helios.simplex.domain.problem.VariableType.SLACK;

public class VariableTestBuilder {

	private String name;
	private VariableType type;
	private int id = UNDEFINED_ID;

	private VariableTestBuilder(VariableType type) {
		this.type = type;
	}

	public static VariableTestBuilder createOriginalVariable() {
		return new VariableTestBuilder(ORIGINAL);
	}

	public static VariableTestBuilder createSlackVariable() {
		return new VariableTestBuilder(SLACK);
	}

	public static VariableTestBuilder createArtificialVariable() {
		return new VariableTestBuilder(ARTIFICIAL);
	}

	public VariableTestBuilder id(int id) {
		this.id = id;
		return this;
	}

	public VariableTestBuilder name(String name) {
		this.name = name;
		return this;
	}

	public VariableTestBuilder type(VariableType type) {
		this.type = type;
		return this;
	}

	public Variable build() {
		switch (this.type) {
		case ORIGINAL:
			return newOriginalVariable(this.name);
		case SLACK:
			return newSlackVariable();
		case ARTIFICIAL:
			return newArtificialVariable();
		default:
			throw new IllegalArgumentException("Invalid type");
		}
	}
}
