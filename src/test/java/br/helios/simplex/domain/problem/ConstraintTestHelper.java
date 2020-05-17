package br.helios.simplex.domain.problem;

import static br.helios.simplex.domain.problem.Variables.getByName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import br.helios.simplex.domain.problem.parser.ConstraintParser;

public class ConstraintTestHelper {

	public static void assertOperator(String inputConstraint, Operator expectedOperator) {
		Constraint constraint = new ConstraintParser().parse(inputConstraint, 1);
		assertOperator(constraint, expectedOperator);
	}

	public static void assertOperator(Constraint constraint, Operator expectedOperator) {
		assertEquals(expectedOperator, constraint.getOperator());
	}

	public static void assertValue(String inputConstraint, double expectedValue) {
		Constraint constraint = new ConstraintParser().parse(inputConstraint, 1);
		assertValue(constraint, expectedValue);
	}

	public static void assertValue(Constraint constraint, double expectedValue) {
		assertEquals(new Double(expectedValue), new Double(constraint.getConstraintValue()));
	}

	public static void assertConstraint(Constraint constraint, Operator expectedOperator, double expectedValue) {
		assertValue(constraint, expectedValue);
		assertOperator(constraint, expectedOperator);
	}

	public static void assertTermCreated(String inputConstraint, String variableName, double coefficient) {
		Constraint constraint = parse(inputConstraint);
		assertTermCreated(constraint, variableName, coefficient);
	}

	public static void assertTermCreated(Constraint constraint, String variableName, double coefficient) {
		Variable variable = getByName(variableName);
		Term term = constraint.getTermByVariable(variable);
		assertThat(term.getCoefficient(), is(coefficient));
	}

	private static Constraint parse(String constraint) {
		return new ConstraintParser().parse(constraint, 1);
	}
}
