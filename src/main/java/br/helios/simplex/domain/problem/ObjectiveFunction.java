package br.helios.simplex.domain.problem;

import java.util.ArrayList;
import java.util.List;

public class ObjectiveFunction {

	private final Objective objective;
	private final List<Term> terms;

	public ObjectiveFunction(Objective objective, List<Term> terms) {
		this.objective = objective;
		this.terms = terms;
	}

	public ObjectiveFunction(ObjectiveFunction anotherObjectiveFunction) {
		this.objective = anotherObjectiveFunction.objective;
		this.terms = new ArrayList<Term>(anotherObjectiveFunction.terms);
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

	public List<Variable> getVariables() {
		List<Variable> variables = new ArrayList<Variable>();
		for (Term term : terms) {
			variables.add(term.getVariable());
		}
		return variables;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(objective.description() + " z = ");
		for (Term termo : terms) {
			buffer.append(termo.toString() + " ");
		}
		return buffer.toString();
	}
}
