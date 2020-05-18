package br.helios.simplex.domain.problem.parser;

import static br.helios.simplex.domain.problem.ConstraintTestHelper.assertOperator;
import static br.helios.simplex.domain.problem.ConstraintTestHelper.assertTermCreated;
import static br.helios.simplex.domain.problem.ConstraintTestHelper.assertValue;
import static br.helios.simplex.domain.problem.Operator.EQUAL;
import static br.helios.simplex.domain.problem.Operator.GREATER_EQUAL;
import static br.helios.simplex.domain.problem.Operator.LESS_EQUAL;
import static br.helios.simplex.domain.problem.parser.ParserVariables.getVariables;

import org.junit.Before;
import org.junit.Test;

public class ConstraintParserTest {

	@Before
	public void setUp() {
		ParserVariables.clear();
	}

	@Test
	public void testConstraintOperator() {
		// verify
		assertOperator("x1 = 10", EQUAL);
		assertOperator("x1 >= 10", GREATER_EQUAL);
		assertOperator("x1 <= 10", LESS_EQUAL);
	}

	@Test
	public void testConstraintValue() {
		// verify
		assertValue("x1 = 11", 11);
		assertValue("x1 = -11", -11);
		assertValue("x1 >=10", 10);
		assertValue("x1 >=   0.5", 0.5);
		assertValue("x1 <=  -10", -10);
		assertValue("x1 <= 0.005", 0.005);
	}

	@Test
	public void testConstraintTermsWithLessEqual() {
		// setup
		String inputData = "0.5x1 - 2.0x2 -0.0005x3+100x4 -  200x5   <= 10";
		// verify
		assertTermCreated(inputData, "x1", 0.5, getVariables());
		assertTermCreated(inputData, "x2", -2, getVariables());
		assertTermCreated(inputData, "x3", -0.0005, getVariables());
		assertTermCreated(inputData, "x4", 100, getVariables());
		assertTermCreated(inputData, "x5", -200, getVariables());
	}

	@Test
	public void testConstraintTermsWithGreaterThan() {
		// setup
		String inputData = "0.5x1 - 2.0x2 -0.0005x3+100x4 -  200x5   >= 10";
		// verify
		assertTermCreated(inputData, "x1", 0.5, getVariables());
		assertTermCreated(inputData, "x2", -2, getVariables());
		assertTermCreated(inputData, "x3", -0.0005, getVariables());
		assertTermCreated(inputData, "x4", 100, getVariables());
		assertTermCreated(inputData, "x5", -200, getVariables());
	}

	@Test
	public void testConstraintTermsWithEqual() {
		// setup
		String inputData = "0.5x1 - 2.0x2 -0.0005x3+100x4 -  200x5  = 10";
		// verify
		assertTermCreated(inputData, "x1", 0.5, getVariables());
		assertTermCreated(inputData, "x2", -2, getVariables());
		assertTermCreated(inputData, "x3", -0.0005, getVariables());
		assertTermCreated(inputData, "x4", 100, getVariables());
		assertTermCreated(inputData, "x5", -200, getVariables());
	}
}
