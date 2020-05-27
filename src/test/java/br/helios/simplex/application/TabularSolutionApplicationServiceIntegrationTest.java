package br.helios.simplex.application;

import static br.helios.simplex.domain.problem.TabularSolutionTestHelper.assertSolution;
import static java.util.Arrays.asList;

import java.math.BigDecimal;

import org.junit.Test;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.ProblemInstanceTestBuilder;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

public class TabularSolutionApplicationServiceIntegrationTest {

	private static final BigDecimal TEST_ZERO = new BigDecimal("0.00000");

	@Test
	public void testSimpleProblemB() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemB();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("36.00000"), asList(new BigDecimal("2.00000"), new BigDecimal("6.00000")), solution, problem.variables);
		assertSolution(new BigDecimal("36.00000"), dualSolution);
	}

	@Test
	public void testDualSimpleProblemB() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemB();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("36.00000"), asList(new BigDecimal("2.00000"), new BigDecimal("6.00000")), solution, problem.variables);
		assertSolution(new BigDecimal("36.00000"), dualSolution);
	}

	@Test
	public void testSimpleProblemC() {
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemC();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("28.00000"), asList(new BigDecimal("5.00000"), new BigDecimal("6.00000")), solution, problem.variables);
		assertSolution(new BigDecimal("28.00000"), dualSolution);
	}

	@Test
	public void testSimpleProblemD() {
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemD();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("1140000.00000"), asList(new BigDecimal("600.00000"), TEST_ZERO, TEST_ZERO), solution, problem.variables);
		assertSolution(new BigDecimal("1140000.00000"), dualSolution);
	}

	@Test
	public void testSimpleMinimizationProblemA() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleMinimizationProblemA();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("-8.00000"), asList(new BigDecimal("4.00000"), TEST_ZERO), solution, problem.variables);
		assertSolution(new BigDecimal("-8.00000"), dualSolution);
	}

	@Test
	public void testSimpleMinimizationProblemB() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleMinimizationProblemB();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("-11.33333"), solution);
		assertSolution(new BigDecimal("-11.33333"), dualSolution);
	}

	@Test
	public void testProblemWithEquityAndGreaterEqualConstraints() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildProblemWithEquityAndGreaterEqualConstraints();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("5.25000"), solution);
		assertSolution(new BigDecimal("5.25000"), dualSolution);
	}

	@Test
	public void testProblemWithEquityAndGreaterEqualAndNegativeConstraints() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildProblemWithEquityAndGreaterEqualAndNegativeConstraints();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("5.25000"), solution);
		assertSolution(new BigDecimal("5.25000"), dualSolution);
	}

	@Test
	public void testMinimizationProblemWithGreaterEqualConstraints() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithGreaterEqualConstraints();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("0.66000"), solution);
		assertSolution(new BigDecimal("0.66000"), dualSolution);
	}

	@Test
	public void testMinimizationProblemWithGreaterEqualConstraintsB() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithGreaterEqualConstraintsB();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("36.00000"), solution);
		assertSolution(new BigDecimal("36.00000"), dualSolution);
	}

	@Test
	public void testMinimizationProblemWithGreaterEqualConstraintsC() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithGreaterEqualConstraintsC();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("7.00000"), solution);
		assertSolution(new BigDecimal("7.00000"), dualSolution);
	}

	@Test
	public void testMinimizationProblemWithFiveTerms() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithFiveTerms();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("-100.00000"), solution);
		assertSolution(new BigDecimal("-100.00000"), dualSolution);
	}

}