package br.helios.simplex.domain.problem;

import static java.math.RoundingMode.HALF_UP;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import br.helios.simplex.domain.problem.variable.Variables;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

public class TabularSolutionTestHelper {

	public static void assertSolution(BigDecimal expectedSolutionVariable, TabularSolution solution) {
		assertThat(solution.getSolutionValue().setScale(5, HALF_UP), is(expectedSolutionVariable));
	}

	public static void assertSolution(BigDecimal expectedSolutionVariable, List<BigDecimal> variableValues, TabularSolution solution, Variables variables) {
		assertSolution(expectedSolutionVariable, solution);
		int numOriginalVariables = variables.getOriginalVariables().size();
		for (int j = 0; j < numOriginalVariables - 1; j++) {
			assertThat(solution.variables.get(j).value(solution.simplexTable).setScale(5, HALF_UP), is(variableValues.get(j)));
		}
	}
}
