package br.helios.simplex.domain.problem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class VariableTestHelper {

	public static void assertVariable(Variable variable, String expectedName, boolean expectedOriginal, int expectedId) {
		assertThat(variable.name, is(expectedName));
		assertTrue(variable.isOriginal);
		assertId(variable, expectedId);
	}

	public static void assertId(Variable variable, int expectedId) {
		assertThat(variable.id(), is(expectedId));
	}
}
