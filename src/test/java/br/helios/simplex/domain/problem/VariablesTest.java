package br.helios.simplex.domain.problem;

import static br.helios.simplex.domain.problem.VariableTestBuilder.createOriginalVariable;
import static br.helios.simplex.domain.problem.VariableTestBuilder.createSlackVariable;
import static br.helios.simplex.domain.problem.VariableTestHelper.assertId;
import static br.helios.simplex.domain.problem.VariableType.ORIGINAL;
import static br.helios.simplex.domain.problem.VariableType.SLACK;
import static br.helios.simplex.domain.problem.Variables.clear;
import static br.helios.simplex.domain.problem.Variables.getById;
import static br.helios.simplex.domain.problem.Variables.getByName;
import static br.helios.simplex.domain.problem.VariablesTestHelper.assertContainsOnly;
import static br.helios.simplex.domain.problem.VariablesTestHelper.assertIsInCorrectList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class VariablesTest {

	private static final String VARIABLE_NAME_1 = "x1";
	private static final String VARIABLE_NAME_2 = "x2";

	@Before
	public void setUp() {
		clear();
	}

	@Test
	public void testNewOriginalVariableCreation() {
		// execute
		Variable variable = createOriginalVariable().name(VARIABLE_NAME_1).type(ORIGINAL).build();

		// verify
		assertId(variable, 1);
		assertContainsOnly(variable);
		assertIsInCorrectList(variable);
	}

	@Test
	public void testSlackVariableCreation() {
		// execute
		Variable variable = createSlackVariable().build();

		// verify
		assertId(variable, 1);
		assertContainsOnly(variable);
		assertIsInCorrectList(variable);
	}

	@Test
	public void testVariableRecoveryById() {
		// setup
		Variable firstVariable = createOriginalVariable().name(VARIABLE_NAME_1).type(SLACK).build();
		Variable secondVariable = createOriginalVariable().name(VARIABLE_NAME_2).type(ORIGINAL).build();

		// verify
		assertThat(getById(firstVariable.id()), is(firstVariable));
		assertThat(getById(secondVariable.id()), is(secondVariable));
	}

	@Test
	public void testVariableRecoveryByName() {
		// setup
		Variable variable = createOriginalVariable().name(VARIABLE_NAME_1).type(SLACK).build();

		// verify
		assertThat(getByName(VARIABLE_NAME_1.toLowerCase()), is(variable));
		assertThat(getByName(VARIABLE_NAME_1.toUpperCase()), is(variable));
		assertNull(getByName(VARIABLE_NAME_2));
	}
}
