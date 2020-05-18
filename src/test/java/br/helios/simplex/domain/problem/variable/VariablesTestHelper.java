package br.helios.simplex.domain.problem.variable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.domain.problem.variable.Variables;

public class VariablesTestHelper {

	public static void assertContainsOnly(Variable variable, Variables variables) {
		assertThat(variables.getVariables().size(), is(1));
		assertThat(variables.getVariables().get(0), is(variable));
	}

	public static void assertIsInCorrectList(Variable variable, Variables variables) {
		assertIsInTheList(variable, variables.getVariables());
		if (variable.isOriginal) {
			assertIsInTheList(variable, variables.getOriginalVariables());
			assertIsNotInTheList(variable, variables.getSlackVariables());
		} else {
			assertIsInTheList(variable, variables.getSlackVariables());
			assertIsNotInTheList(variable, variables.getOriginalVariables());
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
