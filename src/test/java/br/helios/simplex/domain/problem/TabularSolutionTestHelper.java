package br.helios.simplex.domain.problem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import br.helios.simplex.domain.problem.variable.Variables;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

public class TabularSolutionTestHelper {

	public static void assertSolution(double expectedSolutionVariable, TabularSolution solution) {
		assertThat(solution.getSolutionValue(), is(expectedSolutionVariable));
	}

	public static void assertSolution(double expectedSolutionVariable, List<Double> variableValues, TabularSolution solution, Variables variables) {
		assertSolution(expectedSolutionVariable, solution);
		int numOriginalVariables = variables.getOriginalVariables().size();
		for (int j = 0; j < numOriginalVariables - 1; j++) {
			assertThat(solution.variables.get(j).value(solution.simplexTable), is(variableValues.get(j)));
		}
	}
}
