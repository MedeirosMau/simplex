package br.helios.simplex.domain.problem;

import static br.helios.simplex.domain.problem.VariableTestBuilder.createOriginalVariable;

public class TermTestBuilder {

	private double coefficient;
	private Variable variable;

	private TermTestBuilder() {
	}

	public static TermTestBuilder createTerm() {
		return new TermTestBuilder();
	}

	public TermTestBuilder coefficient(double coefficient) {
		this.coefficient = coefficient;
		return this;
	}

	public TermTestBuilder variable(Variable variable) {
		this.variable = variable;
		return this;
	}

	public TermTestBuilder variable(String name, VariableType type) {
		this.variable = createOriginalVariable().name(name).type(type).build();
		return this;
	}

	public Term build() {
		return new Term(this.coefficient, this.variable);
	}
}
