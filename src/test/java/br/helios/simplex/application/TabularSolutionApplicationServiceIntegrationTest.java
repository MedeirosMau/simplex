package br.helios.simplex.application;

import static br.helios.simplex.domain.problem.TabularSolutionTestHelper.assertSolution;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;

import java.math.BigDecimal;

import org.junit.Test;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.ProblemInstanceTestBuilder;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

public class TabularSolutionApplicationServiceIntegrationTest {

	@Test
	public void testSimpleProblemB() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemB();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("36.00000"), asList(new BigDecimal("2.00000"), new BigDecimal("6.00000")), solution, problem.variables);
	}

	@Test
	public void testSimpleProblemC() {
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemC();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("28.00000"), asList(new BigDecimal("5.00000"), new BigDecimal("6.00000")), solution, problem.variables);
	}

	@Test
	public void testSimpleMinimizationProblemA() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleMinimizationProblemA();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("-8.00000"), asList(new BigDecimal("4.00000"), ZERO), solution, problem.variables);
	}

	@Test
	public void testSimpleMinimizationProblemB() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleMinimizationProblemB();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("-11.33333"), solution);
	}

	@Test
	public void testProblemWithEquityAndGreaterEqualConstraints() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildProblemWithEquityAndGreaterEqualConstraints();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("-11.33333"), solution);
	}

}
