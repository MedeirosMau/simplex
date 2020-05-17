package br.helios.simplex.domain.problem;

import static br.helios.simplex.infrastructure.util.StringUtil.hasElement;

public enum Operator {

	LESS_EQUAL("<="), GREATER_EQUAL(">="), LESS("<"), GREATER(">"), EQUAL("=");

	private final String operator;

	private Operator(String operator) {
		this.operator = operator;
	}

	public String operator() {
		return operator;
	}

	public static Operator getOperator(String operator) {
		for (Operator inequationOperator : values()) {
			if (inequationOperator.operator.equals(operator)) {
				return inequationOperator;
			}
		}
		throw new IllegalArgumentException("What operator is this?! " + operator);
	}

	public static Operator getOperatorFrom(String inequation) {
		boolean hasEqualOperator = hasElement(inequation, EQUAL.operator);
		boolean hasLessOperator = hasElement(inequation, LESS.operator);
		boolean hasGreaterOperator = hasElement(inequation, GREATER.operator);

		if (hasEqualOperator) {
			if (hasGreaterOperator) {
				return GREATER_EQUAL;
			} else if (hasLessOperator) {
				return LESS_EQUAL;
			} else {
				return EQUAL;
			}
		} else if (hasGreaterOperator) {
			return GREATER;
		} else if (hasLessOperator) {
			return LESS;
		}

		throw new IllegalArgumentException("Unkown operator");
	}
}
