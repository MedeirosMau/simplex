package br.helios.simplex.domain.problem;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import br.helios.simplex.domain.problem.parser.ObjectiveFunctionParser;
import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.domain.problem.variable.Variables;

public class ObjectiveFunctionTestHelper {

	public static void assertObjective(String inputData, Objective expectedObjective) {
		ObjectiveFunction objectiveFunction = parse(inputData);
		assertEquals(expectedObjective, objectiveFunction.getObjective());
	}

	public static void assertTermCreated(String inputData, String variableName, BigDecimal coefficient, Variables variables) {
		ObjectiveFunction objectiveFunction = parse(inputData);
		assertTermCreated(objectiveFunction, variableName, coefficient, variables);
	}

	public static void assertTermCreated(ObjectiveFunction objectiveFunction, String variableName, BigDecimal coefficient, Variables variables) {
		Variable variable = variables.getByName(variableName);
		Term term = objectiveFunction.getTermByVariable(variable);
		assertThat(term.getCoefficient(), is(coefficient));
	}

	private static ObjectiveFunction parse(String inputData) {
		return new ObjectiveFunctionParser().parse(inputData);
	}
}
