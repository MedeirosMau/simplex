package br.helios.simplex.domain.problem.parser;

import static br.helios.simplex.domain.problem.Objective.MAXIMIZATION;
import static br.helios.simplex.domain.problem.Objective.MINIMIZATION;
import static br.helios.simplex.domain.problem.ObjectiveFunctionTestHelper.assertObjective;
import static br.helios.simplex.domain.problem.ObjectiveFunctionTestHelper.assertTermCreated;

import org.junit.Before;
import org.junit.Test;

import br.helios.simplex.domain.problem.Variables;

public class ObjectiveFunctionParserTest {

	@Before
	public void setUp() {
		Variables.clear();
	}

	@Test
	public void testMaximizationProblem() {
		// verify
		assertObjective("max z = x1", MAXIMIZATION);
	}

	@Test
	public void testMinimizationProblem() {
		// verify
		assertObjective("min z = x1", MINIMIZATION);
	}

	@Test
	public void testTerms() {
		// setup
		String inputData = "max z = x1 - 2x2 -0.5x3 +100x4 + 200x5-8x6";
		// verify
		assertTermCreated(inputData, "x1", 1);
		assertTermCreated(inputData, "x2", -2);
		assertTermCreated(inputData, "x3", -0.5);
		assertTermCreated(inputData, "x4", 100);
		assertTermCreated(inputData, "x5", 200);
		assertTermCreated(inputData, "x6", -8);
	}

}
