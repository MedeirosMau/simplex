package br.helios.simplex.domain.problem;

import static br.helios.simplex.domain.problem.Variable.newOriginalVariable;
import static br.helios.simplex.domain.problem.VariableTestHelper.assertVariable;
import static br.helios.simplex.domain.problem.Variables.clear;

import org.junit.Before;
import org.junit.Test;

public class CreateVariableServiceTest {

	private static final String VARIABLE_NAME = "x";

	@Before
	public void setUp() {
		clear();
	}

	@Test
	public void testCreationOfNewVariable() {
		// execute
		Variable variable = newOriginalVariable(VARIABLE_NAME);

		// verify
		assertVariable(variable, VARIABLE_NAME, true, 1);
	}

	@Test
	public void testCreationOfAlreadyCreatedVariable() {
		// setup
		Variable variable = newOriginalVariable(VARIABLE_NAME);

		// execute
		Variable newVariable = newOriginalVariable(variable.name);

		// verify
		assertVariable(newVariable, VARIABLE_NAME, true, 1);
	}

}
