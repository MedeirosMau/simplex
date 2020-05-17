package br.helios.simplex.domain.problem;

import java.util.ArrayList;
import java.util.List;

public class Constraint {

	private final int order;
	private final List<Term> terms;
	private final Operator operator;
	private final double constraintValue;

	public Constraint(int order, List<Term> terms, Operator operator, double constraintValue) {
		this.order = order;
		this.terms = terms;
		this.operator = operator;
		this.constraintValue = constraintValue;
	}

	public Constraint(Constraint anotherConstraint, Operator operator) {
		this.order = anotherConstraint.order;
		this.terms = new ArrayList<Term>(anotherConstraint.terms);
		this.operator = operator;
		this.constraintValue = anotherConstraint.constraintValue;
	}

	public List<Term> getTerms() {
		return terms;
	}

	public void addTerm(Term term) {
		terms.add(term);
	}

	public boolean contains(Term term) {
		return terms.contains(term);
	}

	public Operator getOperator() {
		return operator;
	}

	public double getConstraintValue() {
		return constraintValue;
	}

	public List<Variable> getVariables() {
		List<Variable> variables = new ArrayList<Variable>();
		for (Term term : terms) {
			variables.add(term.getVariable());
		}
		return variables;
	}

	public Term getTermByVariable(Variable variable) {
		for (Term term : getTerms()) {
			if (term.getVariable().id() == variable.id()) {
				return term;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(" + order + ") ");
		for (Term term : terms) {
			builder.append(term.toString() + " ");
		}
		builder.append(operator.operator() + " " + constraintValue);
		return builder.toString();
	}
}
