package br.helios.simplex.domain.problem;

import java.util.ArrayList;
import java.util.List;

public class ProblemInstanceTestBuilder {

	/**
	 * Own fake problem
	 */
	public static Problem buildSimpleProblemA() {
		String objectiveFunction = "max z = x1 + 2x2 - 3x3";
		List<String> constraints = new ArrayList<String>();
		constraints.add("x1 - x3 <= 10");
		constraints.add("2x1 - 0.5x2 <= 0.5");
		constraints.add("x1 -2x2 - x3 <= 5");
		return build(objectiveFunction, constraints);
	}

	/**
	 * Hillier book
	 */
	public static Problem buildSimpleProblemB() {
		// z = 36, x1 = 2, x2 = 6
		String objectiveFunction = "max z = 3x1 + 5x2";
		List<String> constraints = new ArrayList<String>();
		constraints.add("x1 <= 4");
		constraints.add("2x2 <= 12");
		constraints.add("3x1 + 2x2 <= 18");
		return build(objectiveFunction, constraints);
	}

	/**
	 * http://fourier.eng.hmc.edu/e176/lectures/NM/node32.html
	 */
	public static Problem buildSimpleProblemC() {
		// z = 28, x1 = 5, x2 = 6
		String objectiveFunction = "max z = 2x1 + 3x2";
		List<String> constraints = new ArrayList<String>();
		constraints.add("2x1 + x2 <= 18");
		constraints.add("6x1 + 5x2 <= 60");
		constraints.add("2x1 + 5x2 <= 40");
		return build(objectiveFunction, constraints);
	}

	/**
	 * Hillier
	 */
	public static Problem buildProblemWithEquityConstraint() {
		// z = 28, x1 = 5, x2 = 6
		String objectiveFunction = "max z = 3x1 + 5x2";
		List<String> constraints = new ArrayList<String>();
		constraints.add("x1 <= 4");
		constraints.add("2x2 <= 12");
		constraints.add("3x1 + 2x2 = 18");
		return build(objectiveFunction, constraints);
	}

	/**
	 * https://www.oocities.org/vuumanj/BusinessAlgebra/SimplexMinimize.html
	 */
	public static Problem buildSimpleMinimizationProblemA() {
		// z = 8, x1 = 4, x2 = 0
		String objectiveFunction = "min z = -2x1 + x2";
		List<String> constraints = new ArrayList<String>();
		constraints.add("x1 + 2x2 <= 6");
		constraints.add("3x1 + 2x2 <= 12");
		return build(objectiveFunction, constraints);
	}

	/**
	 * LINDO
	 */
	public static Problem buildSimpleMinimizationProblemB() {
		// z = -11.333333333333334
		String objectiveFunction = "min z = -2x1 + x2 - 5x3";
		List<String> constraints = new ArrayList<String>();
		constraints.add("x1 + 2x2 <= 6");
		constraints.add("3x1 + 5x3 <= 12");
		constraints.add("x3 <= 2");
		return build(objectiveFunction, constraints);
	}

	/**
	 * Hillier
	 */
	public static Problem buildProblemWithEquityAndGreaterEqualConstraints() {
		String objectiveFunction = "min z = 0.4x1 + 0.5x2";
		List<String> constraints = new ArrayList<String>();
		constraints.add("0.3x1 + 0.1x2 <= 2.7");
		constraints.add("0.5x1 + 0.5x2 = 6");
		constraints.add("0.6x1 + 0.4x2 >= 6");
		return build(objectiveFunction, constraints);
	}

	/**
	 * Hillier (with negative constraint)
	 */
	public static Problem buildProblemWithEquityAndGreaterEqualAndNegativeConstraints() {
		String objectiveFunction = "min z = 0.4x1 + 0.5x2";
		List<String> constraints = new ArrayList<String>();
		constraints.add("0.3x1 + 0.1x2 <= 2.7");
		constraints.add("0.5x1 + 0.5x2 = 6");
		constraints.add("-0.6x1 - 0.4x2 <= -6");
		return build(objectiveFunction, constraints);
	}

	/**
	 * https://college.cengage.com/mathematics/larson/elementary_linear/4e/shared/downloads/c09s4.pdf
	 */
	public static Problem buildMinimizationProblemWithGreaterEqualConstraints() {
		String objectiveFunction = "min z = 0.12x1 + 0.15x2";
		List<String> constraints = new ArrayList<String>();
		constraints.add("60x1 + 60x2 >= 300");
		constraints.add("12x1 + 6x2 >= 36");
		constraints.add("10x1 + 30x2 >= 90");
		return build(objectiveFunction, constraints);
	}

	/**
	 * https://college.cengage.com/mathematics/larson/elementary_linear/4e/shared/downloads/c09s4.pdf
	 */
	public static Problem buildMinimizationProblemWithGreaterEqualConstraintsB() {
		String objectiveFunction = "min z = 2x1 +10x2 + 8x3";
		List<String> constraints = new ArrayList<String>();
		constraints.add("x1 + x2 +x3 >= 6");
		constraints.add("x2 + 2x3 >= 8");
		constraints.add("-x1 + 2x2 + 2x3 >= 4");
		return build(objectiveFunction, constraints);
	}

	/**
	 * https://jaredantrobus.com/teaching/2015/Summer/MA162/4.2.php
	 */
	public static Problem buildMinimizationProblemWithGreaterEqualConstraintsC() {
		String objectiveFunction = "min z = 4x1 + 2x2";
		List<String> constraints = new ArrayList<String>();
		constraints.add("5x1 + x2 >= 5");
		constraints.add("5x1 + 3x2 >= 10");
		return build(objectiveFunction, constraints);
	}

	/**
	 * https://courses.lumenlearning.com/sanjacinto-finitemath1/chapter/reading-solving-standard-maximization-problems-using-the-simplex-method/
	 */
	public static Problem buildSimpleProblemD() {
		String objectiveFunction = "max z = 1900x1 + 700x2 + 1000z";
		List<String> constraints = new ArrayList<String>();
		constraints.add("x1 + x2 + x3 <= 600");
		constraints.add("14x2 + 40x3 <= 0");
		return build(objectiveFunction, constraints);
	}

	public static Problem buildMinimizationProblemWithFiveTerms() {
		String objectiveFunction = "min z = 4x1 + 2x2 + 5x3 -4x4 +2x5";
		List<String> constraints = new ArrayList<String>();
		constraints.add("x1 <= 50");
		constraints.add("x2 + x3 >= 10");
		constraints.add("x1 + x4 - x5 <= 100");
		constraints.add("x4 + x5 = 30");
		return build(objectiveFunction, constraints);
	}

	public static Problem build(String objectiveFunction, List<String> constraints) {
		return ProblemTestBuilder.create(objectiveFunction, constraints).build();
	}
}
