package br.helios.simplex.domain.tabularsolution.initialsolution;

import java.util.List;

import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.variable.Variable;

class InitialSimplexTableFactory {

	public double[][] create(Problem artificialProblem) {
		ObjectiveFunction objectiveFunction = artificialProblem.getObjectiveFunction();
		int constraintsNum = artificialProblem.getConstraints().size();
		Variable[] variables = artificialProblem.variables.toArray();

		double[][] table = new double[constraintsNum + 1][variables.length + 1];

		// Insert inverted objective function coefficients

		for (int j = 0; j < variables.length; j++) {
			double variableCoefficient = getCoefficientFromVariable(objectiveFunction, variables[j]);
			table[0][j] = -1 * variableCoefficient;
		}

		table[0][table[0].length - 1] = artificialProblem.getObjectiveFunction().getValue();

		// Insert constraints coefficients

		List<Constraint> constraints = artificialProblem.getConstraints();

		for (int i = 0; i < constraints.size(); i++) {
			Constraint constraint = constraints.get(i);
			for (int j = 0; j < variables.length; j++) {
				double coefficientValue = getCoefficientFromVariable(constraint, variables[j]);
				table[i + 1][j] = coefficientValue;
			}
		}

		// Insert constraints restriction values

		for (int i = 0; i < constraints.size(); i++) {
			table[i + 1][table[i + 1].length - 1] = constraints.get(i).getConstraintValue();
		}

		return table;
	}

	private double getCoefficientFromVariable(Constraint constraint, Variable variable) {
		return getCoefficientFromTerm(constraint.getTermByVariable(variable));
	}

	private double getCoefficientFromVariable(ObjectiveFunction objectiveFunction, Variable variable) {
		return getCoefficientFromTerm(objectiveFunction.getTermByVariable(variable));
	}

	private double getCoefficientFromTerm(Term term) {
		if (term != null) {
			return term.getCoefficient();
		}
		return 0;
	}
}
