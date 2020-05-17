package br.helios.simplex.domain.problem;

import java.util.List;

public class ConstraintTestBuilder {

	private int order;
	private List<Term> terms;
	private Operator operator;
	private double constraintValue;

	private ConstraintTestBuilder(Operator operator) {
		this.operator = operator;
	}

	public static ConstraintTestBuilder createConstraint(Operator operator) {
		return new ConstraintTestBuilder(operator);
	}

	public ConstraintTestBuilder order(int order) {
		this.order = order;
		return this;
	}

	public ConstraintTestBuilder terms(List<Term> terms) {
		this.terms = terms;
		return this;
	}

	public ConstraintTestBuilder value(double constraintValue) {
		this.constraintValue = constraintValue;
		return this;
	}

	public Constraint build() {
		return new Constraint(order, terms, operator, constraintValue);
	}
}
