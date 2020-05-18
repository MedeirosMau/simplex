package br.helios.simplex.domain.problem.parser;

import static br.helios.simplex.domain.problem.Operator.EQUAL;
import static br.helios.simplex.domain.problem.Operator.getOperatorFrom;
import static br.helios.simplex.infrastructure.util.StringUtil.COMA;
import static br.helios.simplex.infrastructure.util.StringUtil.DOT;
import static br.helios.simplex.infrastructure.util.StringUtil.removeEmptyValues;

import java.math.BigDecimal;
import java.util.List;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.Operator;
import br.helios.simplex.domain.problem.Term;

public class ConstraintParser {

	private final TermParser termParser;

	public ConstraintParser() {
		this.termParser = new TermParser();
	}

	public Constraint parse(String inputData, int constraintOrder) {
		String inputNormalized = removeEmptyValues(inputData).replace(COMA, DOT);
		Operator operator = getOperatorFrom(inputNormalized);
		List<Term> terms = termParser.parse(getTerms(inputNormalized, operator));
		BigDecimal constraintValue = getConstraintValue(inputNormalized);
		return new Constraint(constraintOrder, terms, operator, constraintValue);
	}

	private String getTerms(String data, Operator operator) {
		int lastIndex = data.indexOf(EQUAL.operator());
		if (operator != EQUAL) {
			lastIndex = lastIndex - 1;
		}
		return data.substring(0, lastIndex);
	}

	private BigDecimal getConstraintValue(String data) {
		int initialIndex = data.indexOf(EQUAL.operator()) + 1;
		String value = data.substring(initialIndex);
		return new BigDecimal(value);
	}
}
