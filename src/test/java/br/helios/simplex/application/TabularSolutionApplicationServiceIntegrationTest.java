package br.helios.simplex.application;

import static br.helios.simplex.domain.problem.TabularSolutionTestHelper.assertSolution;
import static java.util.Arrays.asList;

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
		assertSolution(36d, asList(2d, 6d), solution, problem.variables);
	}

	@Test
	public void testSimpleProblemC() {
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemC();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(28d, asList(5d, 6d), solution, problem.variables);
	}

	@Test
	public void testSimpleMinimizationProblemA() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleMinimizationProblemA();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(-8d, asList(4d, 0d), solution, problem.variables);
	}

	@Test
	public void testSimpleMinimizationProblemB() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleMinimizationProblemB();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(-11.333333333333334d, solution);
	}

	@Test
	public void testProblemWithEquityAndGreaterEqualConstraints() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildProblemWithEquityAndGreaterEqualConstraints();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(-11.333333333333334d, solution);
	}

}
