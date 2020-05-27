package br.helios.simplex.domain.artificialproblem;

import static br.helios.simplex.domain.problem.Term.createTermInverted;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.Operator;
import br.helios.simplex.domain.problem.Term;

public class ConstraintConverter {

	public Constraint convert(Constraint originalConstraint) {
		int order = originalConstraint.order;
		List<Term> convertedTerms = new ArrayList<>();
		for (Term originalTerm : originalConstraint.getTerms()) {
			convertedTerms.add(createTermInverted(originalTerm));
		}
		Operator operator = originalConstraint.getOperator().invertedOperator();
		BigDecimal constraintValue = originalConstraint.getConstraintValue().negate();
		return new Constraint(order, convertedTerms, operator, constraintValue);
	}
}
