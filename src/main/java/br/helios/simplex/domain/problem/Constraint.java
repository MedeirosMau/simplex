package br.helios.simplex.domain.problem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.variable.Variable;

public class Constraint {

	public final int order;
	private final List<Term> terms;
	private final Operator operator;
	private final BigDecimal constraintValue;

	public Constraint(int order, List<Term> terms, Operator operator, BigDecimal constraintValue) {
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

	public BigDecimal getConstraintValue() {
		return constraintValue;
	}

	public Term getTermByVariable(Variable variable) {
		for (Term term : getTerms()) {
			if (term.getVariable().id() == variable.id()) {
				return term;
			}
		}
		return null;
	}

	public Term getArtificialTerm() {
		for (Term term : getTerms()) {
			if (term.isArtificialWithNonZeroCoefficient()) {
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
		builder.append(operator.operator() + " " + constraintValue.toString());
		return builder.toString();
	}
}
