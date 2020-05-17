package br.helios.simplex.domain.problem;

import static br.helios.simplex.domain.problem.Variables.getByName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import br.helios.simplex.domain.problem.parser.ObjectiveFunctionParser;

public class ObjectiveFunctionTestHelper {

	public static void assertObjective(String inputData, Objective expectedObjective) {
		ObjectiveFunction objectiveFunction = parse(inputData);
		assertEquals(expectedObjective, objectiveFunction.getObjective());
	}

	public static void assertTermCreated(String inputData, String variableName, double coefficient) {
		ObjectiveFunction objectiveFunction = parse(inputData);
		assertTermCreated(objectiveFunction, variableName, coefficient);
	}

	public static void assertTermCreated(ObjectiveFunction objectiveFunction, String variableName, double coefficient) {
		Variable variable = getByName(variableName);
		Term term = objectiveFunction.getTermByVariable(variable);
		assertThat(term.getCoefficient(), is(coefficient));
	}

	private static ObjectiveFunction parse(String inputData) {
		return new ObjectiveFunctionParser().parse(inputData);
	}
}
