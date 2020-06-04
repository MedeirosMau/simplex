package br.helios.simplex.domain.problem;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.variable.Variable;

public class ObjectiveFunction {

	private final Objective objective;
	private final List<Term> terms;
	private final BigDecimal value;

	public ObjectiveFunction(Objective objective, List<Term> terms) {
		this(objective, terms, ZERO);
	}

	public ObjectiveFunction(Objective objective, List<Term> terms, BigDecimal value) {
		this.objective = objective;
		this.terms = terms;
		this.value = value;
	}

	public ObjectiveFunction(ObjectiveFunction anotherObjectiveFunction) {
		this.objective = anotherObjectiveFunction.objective;
		this.terms = new ArrayList<Term>(anotherObjectiveFunction.terms);
		this.value = anotherObjectiveFunction.value;
	}

	public Objective getObjective() {
		return objective;
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

	public Term getTermByVariable(Variable variable) {
		for (Term term : getTerms()) {
			if (term.getVariable().id() == variable.id()) {
				return term;
			}
		}
		return null;
	}

	public BigDecimal getValue() {
		return value;
	}

	public boolean hasBigMTerm() {
		for (Term term : getTerms()) {
			if (term.isBigM()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(objective.description() + " ");
		for (int i = 0; i < terms.size(); i++) {
			Term term = terms.get(i);
			String termStr = term.toString();
			if (i > 0 && term.getCoefficient().signum() >= 0) {
				termStr = "+" + termStr;
			}
			buffer.append(termStr + " ");
		}
		return buffer.toString();
	}
}
