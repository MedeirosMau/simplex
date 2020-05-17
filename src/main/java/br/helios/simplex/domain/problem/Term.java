package br.helios.simplex.domain.problem;

public class Term {

	private final double coefficient;
	private final Variable variable;

	public Term(double coefficient, Variable variable) {
		this.coefficient = coefficient;
		this.variable = variable;
	}

	public double getCoefficient() {
		return coefficient;
	}

	public Variable getVariable() {
		return variable;
	}

	@Override
	public String toString() {
		return coefficient + variable.name;
	}
}
