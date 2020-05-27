package br.helios.simplex.application;

import static br.helios.simplex.domain.problem.TabularSolutionTestHelper.assertSolution;

import java.math.BigDecimal;

import org.junit.Test;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.ProblemInstanceTestBuilder;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

public class TabularSolutionApplicationServiceIntegrationDualTest {

	@Test
	public void testSimpleProblemBDual() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemB();
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("36.00000"), dualSolution);
	}

	@Test
	public void testSimpleProblemCDual() {
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemC();
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("28.00000"), dualSolution);
	}

	@Test
	public void testSimpleProblemDDual() {
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemD();
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("1140000.00000"), dualSolution);
	}

	@Test
	public void testSimpleMinimizationProblemADual() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleMinimizationProblemA();
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("-8.00000"), dualSolution);
	}

	@Test
	public void testSimpleMinimizationProblemBDual() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleMinimizationProblemB();
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("-11.33333"), dualSolution);
	}

	@Test
	public void testProblemWithEquityAndGreaterEqualConstraintsDual() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildProblemWithEquityAndGreaterEqualConstraints();
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("5.25000"), dualSolution);
	}

	@Test
	public void testProblemWithEquityAndGreaterEqualAndNegativeConstraintsDual() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildProblemWithEquityAndGreaterEqualAndNegativeConstraints();
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("5.25000"), dualSolution);
	}

	@Test
	public void testMinimizationProblemWithGreaterEqualConstraintsDual() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithGreaterEqualConstraints();
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("0.66000"), dualSolution);
	}

	@Test
	public void testMinimizationProblemWithGreaterEqualConstraintsBDual() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithGreaterEqualConstraintsB();
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("36.00000"), dualSolution);
	}

	@Test
	public void testMinimizationProblemWithGreaterEqualConstraintsCDual() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithGreaterEqualConstraintsC();
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("7.00000"), dualSolution);
	}

	@Test
	public void testMinimizationProblemWithFiveTermsDual() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithFiveTerms();
		TabularSolution dualSolution = new TabularSolverApplicationService().solveDual(problem);
		// verify
		assertSolution(new BigDecimal("-100.00000"), dualSolution);
	}

}