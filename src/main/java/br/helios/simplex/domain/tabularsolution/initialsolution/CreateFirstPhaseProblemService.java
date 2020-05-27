package br.helios.simplex.domain.tabularsolution.initialsolution;

import static br.helios.simplex.domain.problem.Objective.MINIMIZATION;
import static br.helios.simplex.domain.problem.Term.createTerm;
import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.helios.simplex.domain.artificialproblem.ProblemObjectiveConverter;
import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.domain.problem.variable.Variables;
import br.helios.simplex.infrastructure.util.MathContextUtil;

class CreateFirstPhaseProblemService {

	private final ProblemObjectiveConverter problemObjectiveConverter;

	public CreateFirstPhaseProblemService() {
		this.problemObjectiveConverter = new ProblemObjectiveConverter();
	}

	public Problem create(Problem artificialProblem) {
		ObjectiveFunction newObjectiveFunction = createNewObjectiveFunctionTerms(artificialProblem);
		Variables newVariables = artificialProblem.variables.clone();
		return new Problem(newObjectiveFunction, artificialProblem.getConstraints(), newVariables, artificialProblem);
	}

	private ObjectiveFunction createNewObjectiveFunctionTerms(Problem artificialProblem) {
		List<Constraint> constraintsWithArtificialTerms = new ArrayList<>();

		// Find all constraints with artificial variables

		for (Constraint constraint : artificialProblem.getConstraints()) {
			Term artificialTerm = constraint.getArtificialTerm();
			if (artificialTerm != null) {
				constraintsWithArtificialTerms.add(constraint);
			}
		}

		// Variables and their terms on given constraints

		Map<Variable, List<Term>> variableTerms = new HashMap<>();

		for (Constraint constraint : constraintsWithArtificialTerms) {
			for (Term term : constraint.getTerms()) {
				if (!term.getVariable().isArtificial) {
					Variable variable = term.getVariable();
					List<Term> terms = variableTerms.get(variable);
					if (terms == null) {
						terms = new ArrayList<>();
						variableTerms.put(variable, terms);
					}
					terms.add(term);
				}
			}
		}

		// Objective function terms

		List<Term> objectiveFunctionTerms = new ArrayList<>();

		for (Variable variable : artificialProblem.variables.getVariables()) {
			BigDecimal coefficient = ZERO;
			if (variableTerms.containsKey(variable)) {
				for (Term term : variableTerms.get(variable)) {
					coefficient = coefficient.add(term.getCoefficient(), MATH_CONTEXT);
				}
			}
			objectiveFunctionTerms.add(createTerm(coefficient.negate(MathContextUtil.MATH_CONTEXT), variable));
		}

		// Objective function value

		BigDecimal objectiveFunctionValue = ZERO;

		for (Constraint constraint : constraintsWithArtificialTerms) {
			objectiveFunctionValue = objectiveFunctionValue.add(constraint.getConstraintValue(), MATH_CONTEXT);
		}

		ObjectiveFunction objectiveFunction = new ObjectiveFunction(MINIMIZATION, objectiveFunctionTerms, objectiveFunctionValue);
		return problemObjectiveConverter.convert(objectiveFunction);
	}
}
