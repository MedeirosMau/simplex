package br.helios.simplex.domain.problem.variable;

import static br.helios.simplex.domain.problem.Operator.EQUAL;
import static br.helios.simplex.domain.problem.variable.Variable.UNDEFINED_ID;
import static br.helios.simplex.domain.problem.variable.VariableType.ARTIFICIAL;
import static br.helios.simplex.domain.problem.variable.VariableType.ORIGINAL;
import static br.helios.simplex.domain.problem.variable.VariableType.SLACK;
import static java.math.BigDecimal.ZERO;

import java.util.Collections;

import br.helios.simplex.domain.problem.Constraint;

public class VariableTestBuilder {

	private static final Constraint CONSTRAINT = new Constraint(0, Collections.emptyList(), EQUAL, ZERO);

	private String name;
	private VariableType type;
	private int id = UNDEFINED_ID;
	private Variables variables;

	private VariableTestBuilder(VariableType type, Variables variables) {
		this.type = type;
		this.variables = variables;
	}

	public static VariableTestBuilder createOriginalVariable(Variables variables) {
		return new VariableTestBuilder(ORIGINAL, variables);
	}

	public static VariableTestBuilder createSlackVariable(Variables variables) {
		return new VariableTestBuilder(SLACK, variables);
	}

	public static VariableTestBuilder createArtificialVariable(Variables variables) {
		return new VariableTestBuilder(ARTIFICIAL, variables);
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
			return new CreateVariableService().createOriginalVariable(name, variables);
		case SLACK:
			return new CreateVariableService().createSlackVariable(variables, CONSTRAINT, false);
		case ARTIFICIAL:
			return new CreateVariableService().createArtificialVariable(variables, CONSTRAINT, false);
		default:
			throw new IllegalArgumentException("Invalid type");
		}
	}
}
