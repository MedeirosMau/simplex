package br.helios.simplex.domain.problem.variable;

import static br.helios.simplex.domain.problem.variable.VariableTestBuilder.createOriginalVariable;
import static br.helios.simplex.domain.problem.variable.VariableTestBuilder.createSlackVariable;
import static br.helios.simplex.domain.problem.variable.VariableTestHelper.assertId;
import static br.helios.simplex.domain.problem.variable.VariableType.ORIGINAL;
import static br.helios.simplex.domain.problem.variable.VariableType.SLACK;
import static br.helios.simplex.domain.problem.variable.VariablesTestHelper.assertContainsOnly;
import static br.helios.simplex.domain.problem.variable.VariablesTestHelper.assertIsInCorrectList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class VariablesTest {

	private static final String VARIABLE_NAME_1 = "x1";
	private static final String VARIABLE_NAME_2 = "x2";

	@Test
	public void testNewOriginalVariableCreation() {
		// execute
		Variables variables = new Variables();
		Variable variable = createOriginalVariable(variables).name(VARIABLE_NAME_1).type(ORIGINAL).build();

		// verify
		assertId(variable, 1);
		assertContainsOnly(variable, variables);
		assertIsInCorrectList(variable, variables);
	}

	@Test
	public void testSlackVariableCreation() {
		// execute
		Variables variables = new Variables();
		Variable variable = createSlackVariable(variables).build();

		// verify
		assertId(variable, 1);
		assertContainsOnly(variable, variables);
		assertIsInCorrectList(variable, variables);
	}

	@Test
	public void testVariableRecoveryById() {
		// setup
		Variables variables = new Variables();
		Variable firstVariable = createOriginalVariable(variables).name(VARIABLE_NAME_1).type(SLACK).build();
		Variable secondVariable = createOriginalVariable(variables).name(VARIABLE_NAME_2).type(ORIGINAL).build();

		// verify
		assertThat(variables.getById(firstVariable.id()), is(firstVariable));
		assertThat(variables.getById(secondVariable.id()), is(secondVariable));
	}

	@Test
	public void testVariableRecoveryByName() {
		// setup
		Variables variables = new Variables();
		Variable variable = createOriginalVariable(variables).name(VARIABLE_NAME_1).type(SLACK).build();

		// verify
		assertThat(variables.getByName(VARIABLE_NAME_1.toLowerCase()), is(variable));
		assertThat(variables.getByName(VARIABLE_NAME_1.toUpperCase()), is(variable));
		assertNull(variables.getByName(VARIABLE_NAME_2));
	}
}
