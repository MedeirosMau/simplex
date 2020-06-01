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
		// verify
		assertSolution(new BigDecimal("36.00000"), asList(new BigDecimal("2.00000"), new BigDecimal("6.00000")), solution, problem.variables);
//		OBJ COEFFICIENT RANGES
// 		VARIABLE         CURRENT        ALLOWABLE        ALLOWABLE
//                   COEF          INCREASE         DECREASE
//       X1        3.000000         4.500000         3.000000
//       X2        5.000000         INFINITY         3.000000
//
//                           RIGHTHAND SIDE RANGES
//      ROW         CURRENT        ALLOWABLE        ALLOWABLE
//                    RHS          INCREASE         DECREASE
//        2        4.000000         INFINITY         2.000000
//        3       12.000000         6.000000         6.000000
//        4       18.000000         6.000000         6.000000
	}

	@Test
	public void testSimpleProblemC() {
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemC();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("28.00000"), asList(new BigDecimal("5.00000"), new BigDecimal("6.00000")), solution, problem.variables);
//        OBJ COEFFICIENT RANGES
//		VARIABLE         CURRENT        ALLOWABLE        ALLOWABLE
//		COEF          INCREASE         DECREASE
//		X1        2.000000         1.600000         0.800000
//		X2        3.000000         2.000000         1.333333
//
//		        RIGHTHAND SIDE RANGES
//		ROW         CURRENT        ALLOWABLE        ALLOWABLE
//		 RHS          INCREASE         DECREASE
//		2       18.000000         INFINITY         2.000000
//		3       60.000000         5.000000        20.000000
//		4       40.000000        20.000000        10.000000
	}

	@Test
	public void testSimpleProblemD() {
		Problem problem = ProblemInstanceTestBuilder.buildSimpleProblemD();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("1140000.00000"), asList(new BigDecimal("600.00000"), TEST_ZERO, TEST_ZERO), solution, problem.variables);
//      OBJ COEFFICIENT RANGES
//		VARIABLE         CURRENT        ALLOWABLE        ALLOWABLE
//		COEF          INCREASE         DECREASE
//		X1     1900.000000         INFINITY       900.000000
//		X2      700.000000      1200.000000         INFINITY
//		X3     1000.000000       900.000000         INFINITY
//
//		        RIGHTHAND SIDE RANGES
//		ROW         CURRENT        ALLOWABLE        ALLOWABLE
//		 RHS          INCREASE         DECREASE
//		2      600.000000         INFINITY       600.000000
//		3        0.000000         INFINITY         0.000000
	}

	@Test
	public void testSimpleMinimizationProblemA() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleMinimizationProblemA();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("-8.00000"), asList(new BigDecimal("4.00000"), TEST_ZERO), solution, problem.variables);
//        OBJ COEFFICIENT RANGES
//		VARIABLE         CURRENT        ALLOWABLE        ALLOWABLE
//		COEF          INCREASE         DECREASE
//		X1       -2.000000         2.000000         INFINITY
//		X2        1.000000         INFINITY         2.333333
//
//		        RIGHTHAND SIDE RANGES
//		ROW         CURRENT        ALLOWABLE        ALLOWABLE
//		 RHS          INCREASE         DECREASE
//		2        6.000000         INFINITY         2.000000
//		3       12.000000         6.000000        12.000000
	}

	@Test
	public void testSimpleMinimizationProblemB() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildSimpleMinimizationProblemB();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("-11.33333"), solution);
//        OBJ COEFFICIENT RANGES
//		VARIABLE         CURRENT        ALLOWABLE        ALLOWABLE
//		COEF          INCREASE         DECREASE
//		X1       -2.000000         2.000000         1.000000
//		X2        1.000000         INFINITY         1.000000
//		X3       -5.000000         1.666667         INFINITY
//
//		        RIGHTHAND SIDE RANGES
//		ROW         CURRENT        ALLOWABLE        ALLOWABLE
//		 RHS          INCREASE         DECREASE
//		2        6.000000         INFINITY         5.333333
//		3       12.000000        16.000000         2.000000
//		4        2.000000         0.400000         2.000000
	}

	@Test
	public void testProblemWithEquityAndGreaterEqualConstraints() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildProblemWithEquityAndGreaterEqualConstraints();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("5.25000"), solution);

//        OBJ COEFFICIENT RANGES
//		VARIABLE         CURRENT        ALLOWABLE        ALLOWABLE
//		COEF          INCREASE         DECREASE
//		X1        0.400000         0.100000         INFINITY
//		X2        0.500000         INFINITY         0.100000
//
//		        RIGHTHAND SIDE RANGES
//		ROW         CURRENT        ALLOWABLE        ALLOWABLE
//		 RHS          INCREASE         DECREASE
//		2        2.700000         0.900000         0.300000
//		3        6.000000         7.500000         0.500000
//		4        6.000000         0.300000         INFINITY
	}

	@Test
	public void testProblemWithEquityAndGreaterEqualAndNegativeConstraints() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildProblemWithEquityAndGreaterEqualAndNegativeConstraints();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("5.25000"), solution);
	}

	@Test
	public void testMinimizationProblemWithGreaterEqualConstraints() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithGreaterEqualConstraints();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("0.66000"), solution);
	}

	@Test
	public void testMinimizationProblemWithGreaterEqualConstraintsB() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithGreaterEqualConstraintsB();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("36.00000"), solution);
	}

	@Test
	public void testMinimizationProblemWithGreaterEqualConstraintsC() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithGreaterEqualConstraintsC();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("7.00000"), solution);
	}

	@Test
	public void testMinimizationProblemWithFiveTerms() {
		// execute
		Problem problem = ProblemInstanceTestBuilder.buildMinimizationProblemWithFiveTerms();
		TabularSolution solution = new TabularSolverApplicationService().solve(problem);
		// verify
		assertSolution(new BigDecimal("-100.00000"), solution);
	}

}