package br.helios.simplex.domain.problem;

import static br.helios.simplex.domain.problem.Variables.getSlackVariables;
import static br.helios.simplex.domain.problem.Variables.getOriginalVariables;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

public class VariablesTestHelper {

	public static void assertContainsOnly(Variable variable) {
		assertThat(Variables.getVariables().size(), is(1));
		assertThat(Variables.getVariables().get(0), is(variable));
	}

	public static void assertIsInCorrectList(Variable variable) {
		assertIsInTheList(variable, Variables.getVariables());
		if (variable.isOriginal) {
			assertIsInTheList(variable, getOriginalVariables());
			assertIsNotInTheList(variable, getSlackVariables());
		} else {
			assertIsInTheList(variable, getSlackVariables());
			assertIsNotInTheList(variable, getOriginalVariables());
		}
	}

	public static void assertIsInTheList(Variable expectedVariable, List<Variable> variables) {
		for (Variable variable : variables) {
			if (variable.equals(expectedVariable)) {
				return;
			}
		}
		fail("Variable is not in the list");
	}

	public static void assertIsNotInTheList(Variable expectedVariable, List<Variable> variables) {
		for (Variable variable : variables) {
			if (variable.equals(expectedVariable)) {
				fail("Variable is in the list");
			}
		}
	}
}
