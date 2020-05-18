package br.helios.simplex.domain.artificialproblem;

import static br.helios.simplex.domain.problem.ConstraintTestHelper.assertConstraint;
import static br.helios.simplex.domain.problem.ConstraintTestHelper.assertTermCreated;
import static br.helios.simplex.domain.problem.ObjectiveFunctionTestHelper.assertTermCreated;
import static br.helios.simplex.domain.problem.Operator.EQUAL;
import static br.helios.simplex.domain.problem.ProblemInstanceTestBuilder.buildSimpleProblemA;
import static br.helios.simplex.domain.problem.variable.Variable.FAKE_PREFIX;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.Problem;

public class CreateArtificialProblemServiceTest {

	@Test
	public void testArtificialSimpleProblem() {
		// setup
		Problem problem = buildSimpleProblemA();

		// execute
		Problem artificialProblem = new CreateArtificialProblemService().create(problem);

		// verify
		assertEquals(problem.getObjectiveFunction().getObjective(), artificialProblem.getObjectiveFunction().getObjective());
		assertThat("Number of constraints should not change", problem.getConstraints().size(), is(3));
		assertThat("One variable per constraint should be added", artificialProblem.variables.size(), is(6));

		assertTermCreated(artificialProblem.getObjectiveFunction(), "x4", ZERO, artificialProblem.variables);
		assertTermCreated(artificialProblem.getObjectiveFunction(), "x5", ZERO, artificialProblem.variables);
		assertTermCreated(artificialProblem.getObjectiveFunction(), "x6", ZERO, artificialProblem.variables);

		for (int i = 0; i < artificialProblem.getConstraints().size(); i++) {
			Constraint originalConstraint = problem.getConstraints().get(i);
			Constraint newConstraint = artificialProblem.getConstraints().get(i);

			int originalTermsSize = originalConstraint.getTerms().size();
			int newTermsSize = newConstraint.getTerms().size();
			assertThat("Only one term is added", newTermsSize, is(originalTermsSize + 1));

			assertConstraint(newConstraint, EQUAL, originalConstraint.getConstraintValue());

			int originalVariablesSize = artificialProblem.variables.getOriginalVariables().size();

			assertTermCreated(newConstraint, FAKE_PREFIX + (originalVariablesSize + i + 1), ONE, artificialProblem.variables);
		}
	}

}
