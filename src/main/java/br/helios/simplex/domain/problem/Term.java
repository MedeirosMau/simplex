package br.helios.simplex.domain.problem;

import static java.lang.Double.MAX_VALUE;
import static java.lang.String.valueOf;

import br.helios.simplex.domain.problem.variable.Variable;

public class Term {

	public static final String BIG_M = "M";

	private final double coefficient;
	private final Variable variable;
	private final boolean bigM;

	private Term(double coefficient, Variable variable, boolean bigM) {
		this.coefficient = coefficient;
		this.variable = variable;
		this.bigM = bigM;
	}

	public static Term createTerm(double coefficient, Variable variable) {
		return new Term(coefficient, variable, false);
	}

	public static Term createBigMTerm(Variable variable) {
		return new Term(MAX_VALUE, variable, true);
	}

	public static Term createNegativeBigMTerm(Variable variable) {
		return new Term(-MAX_VALUE, variable, true);
	}

	public static Term createTermInverted(Term term) {
		return new Term(-1 * term.getCoefficient(), term.getVariable(), term.isBigM());
	}

	public double getCoefficient() {
		return coefficient;
	}

	public Variable getVariable() {
		return variable;
	}

	public boolean isArtificialWithNonZeroCoefficient() {
		return variable.isArtificial && coefficient != 0;
	}

	public boolean isBigM() {
		return bigM;
	}

	@Override
	public String toString() {
		String coefficientStr = valueOf(coefficient);
		if (isBigM()) {
			if (coefficient >= 0) {
				coefficientStr = "+" + BIG_M;
			} else {
				coefficientStr = "-" + BIG_M;
			}
		}
		return coefficientStr + variable.name;
	}
}
