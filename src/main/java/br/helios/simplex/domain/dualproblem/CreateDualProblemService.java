package br.helios.simplex.domain.dualproblem;

import static br.helios.simplex.domain.problem.Term.createTerm;
import static br.helios.simplex.domain.problem.variable.Variable.DUAL_PREFIX;

import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.artificialproblem.ConstraintConverter;
import br.helios.simplex.domain.artificialproblem.ProblemObjectiveConverter;
import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.Objective;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Operator;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.variable.CreateVariableService;
import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.domain.problem.variable.Variables;

public class CreateDualProblemService {

	private final CreateVariableService createVariableService;
	private final ProblemObjectiveConverter problemObjectiveConverter;
	private final ConstraintConverter constraintConverter;

	public CreateDualProblemService() {
		this.createVariableService = new CreateVariableService();
		this.problemObjectiveConverter = new ProblemObjectiveConverter();
		this.constraintConverter = new ConstraintConverter();
	}

	public Problem create(Problem originalProblem) {
		Problem adaptedProblem = adaptProblem(originalProblem);
		// Problem adaptedProblem = originalProblem;

		ObjectiveFunction originalObjectiveFunction = adaptedProblem.getObjectiveFunction();

		// Create new objective function

		Variables variables = new Variables();
		List<Term> newObjectiveFunctionTerms = new ArrayList<>();

		for (int i = 0; i < adaptedProblem.getConstraints().size(); i++) {
			Variable variable = createVariableService.createOriginalVariable(DUAL_PREFIX + (i + 1), variables);
			Constraint constraint = adaptedProblem.getConstraints().get(i);
			newObjectiveFunctionTerms.add(createTerm(constraint.getConstraintValue(), variable));
		}

		ObjectiveFunction newObjectiveFunction = new ObjectiveFunction(originalObjectiveFunction.getObjective().invert(), newObjectiveFunctionTerms);

		// Create new constraints

		List<Constraint> newConstraints = new ArrayList<>();

		for (int originalVariableIndex = 0; originalVariableIndex < originalObjectiveFunction.getTerms().size(); originalVariableIndex++) {
			Term originalObjectiveFunctionTerm = originalObjectiveFunction.getTerms().get(originalVariableIndex);
			Variable originalVariable = originalObjectiveFunctionTerm.getVariable();
			List<Term> newConstraintTerms = new ArrayList<>();
			for (int constraintIndex = 0; constraintIndex < adaptedProblem.getConstraints().size(); constraintIndex++) {
				Constraint constraint = adaptedProblem.getConstraints().get(constraintIndex);
				Term originalConstraintTerm = constraint.getTermByVariable(originalVariable);
				if (originalConstraintTerm != null) {
					Variable newVariableTerm = variables.getById(constraintIndex + 1);
					newConstraintTerms.add(createTerm(originalConstraintTerm.getCoefficient(), newVariableTerm));
				}
			}
			newConstraints.add(new Constraint(originalVariableIndex + 1, newConstraintTerms, getNewConstraintOperator(originalObjectiveFunction),
					originalObjectiveFunctionTerm.getCoefficient()));
		}

		return new Problem(newObjectiveFunction, newConstraints, variables, adaptedProblem, true);
	}

	private Operator getNewConstraintOperator(ObjectiveFunction originalObjectiveFunction) {
		if (originalObjectiveFunction.getObjective() == Objective.MINIMIZATION) {
			return Operator.LESS_EQUAL;
		}
		return Operator.GREATER_EQUAL;
	}

	private Problem adaptProblem(Problem originalProblem) {
		if (originalProblem.getObjective() == Objective.MINIMIZATION) {
			ObjectiveFunction objectiveFunction = originalProblem.getObjectiveFunction();

			List<Constraint> newConstraints = new ArrayList<>();
			for (Constraint constraint : originalProblem.getConstraints()) {
				if (constraint.getOperator() == Operator.LESS_EQUAL) {
					constraint = constraintConverter.convert(constraint);
				} else if (constraint.getOperator() == Operator.EQUAL) {
					constraint = new Constraint(constraint, Operator.GREATER_EQUAL);
				}
				newConstraints.add(constraint);
			}

			return new Problem(objectiveFunction, newConstraints, originalProblem.variables, originalProblem, true);
		}
		return originalProblem;
	}
}
