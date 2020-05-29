package br.helios.simplex.domain.problem;

import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT_OUTPUT;
import static java.lang.Double.MAX_VALUE;
import static java.lang.String.valueOf;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.infrastructure.util.MathContextUtil;

public class Term {

	public static final String BIG_M = "M";
	public static final BigDecimal BIG_M_VALUE = BigDecimal.valueOf(MAX_VALUE);

	private final BigDecimal coefficient;
	private final Variable variable;
	private final boolean bigM;

	private Term(BigDecimal coefficient, Variable variable, boolean bigM) {
		this.coefficient = coefficient;
		this.variable = variable;
		this.bigM = bigM;
	}

	public static Term createTerm(BigDecimal coefficient, Variable variable) {
		return new Term(coefficient, variable, false);
	}

	public static Term createBigMTerm(Variable variable) {
		return new Term(BIG_M_VALUE, variable, true);
	}

	public static Term createNegativeBigMTerm(Variable variable) {
		return new Term(BIG_M_VALUE.negate(MathContextUtil.MATH_CONTEXT), variable, true);
	}

	public static Term createTermInverted(Term term) {
		return new Term(term.getCoefficient().negate(MathContextUtil.MATH_CONTEXT), term.getVariable(), term.isBigM());
	}

	public BigDecimal getCoefficient() {
		return coefficient;
	}

	public Variable getVariable() {
		return variable;
	}

	public boolean isArtificialWithNonZeroCoefficient() {
		return variable.isArtificial && coefficient.compareTo(ZERO) != 0;
	}

	public boolean isBigM() {
		return bigM;
	}

	@Override
	public String toString() {
		String coefficientStr = valueOf(coefficient.round(MATH_CONTEXT_OUTPUT));
		if (isBigM()) {
			if (coefficient.compareTo(BIG_M_VALUE) >= 0) {
				coefficientStr = "+" + BIG_M;
			} else {
				coefficientStr = "-" + BIG_M;
			}
		}
		return coefficientStr + variable.name + (variable.isArtificial ? "'" : "");
	}
}
