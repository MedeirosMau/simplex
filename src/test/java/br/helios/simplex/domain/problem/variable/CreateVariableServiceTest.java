package br.helios.simplex.domain.problem.variable;

import static br.helios.simplex.domain.problem.variable.VariableTestHelper.assertVariable;

import org.junit.Before;
import org.junit.Test;

public class CreateVariableServiceTest {

	private static final String VARIABLE_NAME = "x";

	private CreateVariableService createVariableService;

	@Before
	public void setUp() {
		this.createVariableService = new CreateVariableService();
	}

	@Test
	public void testCreationOfNewVariable() {
		// execute
		Variables variables = new Variables();
		Variable variable = createVariableService.createOriginalVariable(VARIABLE_NAME, variables);

		// verify
		assertVariable(variable, VARIABLE_NAME, true, 1);
	}

	@Test
	public void testCreationOfAlreadyCreatedVariable() {
		// setup
		Variables variables = new Variables();
		createVariableService.createOriginalVariable(VARIABLE_NAME, variables);

		// execute
		Variable newVariable = createVariableService.createOriginalVariable(VARIABLE_NAME, variables);

		// verify
		assertVariable(newVariable, VARIABLE_NAME, true, 1);
	}

}
