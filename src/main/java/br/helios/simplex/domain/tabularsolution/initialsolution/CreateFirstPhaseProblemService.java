package br.helios.simplex.domain.tabularsolution.initialsolution;

import static br.helios.simplex.domain.problem.Objective.MINIMIZATION;
import static br.helios.simplex.domain.problem.Term.createTerm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.helios.simplex.domain.artificialproblem.MinimizationProblemConverter;
import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.domain.problem.variable.Variables;

class CreateFirstPhaseProblemService {

	private final MinimizationProblemConverter minimizationProblemConverter;

	public CreateFirstPhaseProblemService() {
		this.minimizationProblemConverter = new MinimizationProblemConverter();
	}

	public Problem create(Problem artificialProblem) {
		ObjectiveFunction newObjectiveFunction = createNewObjectiveFunctionTerms(artificialProblem);
		Variables newVariables = artificialProblem.variables.clone();
		return new Problem(newObjectiveFunction, artificialProblem.getConstraints(), newVariables);
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
			double coefficient = 0;
			if (variableTerms.containsKey(variable)) {
				for (Term term : variableTerms.get(variable)) {
					coefficient += term.getCoefficient();
				}
			}
			objectiveFunctionTerms.add(createTerm(-1 * coefficient, variable));
		}

		// Objective function value

		double objectiveFunctionValue = 0;

		for (Constraint constraint : constraintsWithArtificialTerms) {
			objectiveFunctionValue += constraint.getConstraintValue();
		}

		ObjectiveFunction objectiveFunction = new ObjectiveFunction(MINIMIZATION, objectiveFunctionTerms, objectiveFunctionValue);
		return minimizationProblemConverter.convert(objectiveFunction);
	}
}
