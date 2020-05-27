package br.helios.simplex.domain.tabularsolution.initialsolution;

import static br.helios.simplex.domain.problem.Objective.INVERTED_MINIMIZATION;
import static br.helios.simplex.domain.tabularsolution.SolutionVariable.createVariable;
import static br.helios.simplex.infrastructure.util.MathContextUtil.MATH_CONTEXT;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.tabularsolution.DualTabularSolution;
import br.helios.simplex.domain.tabularsolution.PrimalTabularSolution;
import br.helios.simplex.domain.tabularsolution.SolutionVariable;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

public class CreateSecondPhaseInitialSolutionService {

	public TabularSolution createSolution(Problem artificialProblem, Problem firstPhaseProblem, TabularSolution firstPhaseSolution) {
		List<SolutionVariable> firstPhaseSolutionVariables = firstPhaseSolution.variables;

		// Find artificial variables indexes on simplex table

		List<Integer> artificialVariablesPositions = new ArrayList<>();

		for (int j = 0; j < firstPhaseSolutionVariables.size(); j++) {
			SolutionVariable solutionVariable = firstPhaseSolutionVariables.get(j);
			if (solutionVariable.variable.isArtificial) {
				artificialVariablesPositions.add(j);
			}
		}

		// Create new simplex table

		List<SolutionVariable> newSolutionVariables = new ArrayList<>();

		BigDecimal[][] firstPhaseSimplexTable = firstPhaseSolution.simplexTable;
		int artificialVariablesNum = artificialVariablesPositions.size();

		BigDecimal[][] newSimplexTable = new BigDecimal[firstPhaseSimplexTable.length][firstPhaseSimplexTable[0].length - artificialVariablesNum];

		for (int i = 0; i < firstPhaseSimplexTable.length; i++) {
			for (int j = 0, realJ = 0; j < firstPhaseSimplexTable[i].length; j++) {
				BigDecimal positionValue = firstPhaseSimplexTable[i][j];

				// Skip artificial variable column

				if (artificialVariablesPositions.contains(j)) {
					continue;
				}

				if (j < firstPhaseSolutionVariables.size() - 1) {
					SolutionVariable solutionVariable = firstPhaseSolutionVariables.get(j);

					// Update original variables with original coefficients on objective function
					// line

					if (i == 0 && solutionVariable.variable.isOriginal) {
						Term originalTerm = artificialProblem.origin.getObjectiveFunction().getTermByVariable(solutionVariable.variable);
						newSimplexTable[0][j] = originalTerm.getCoefficient();
					} else {
						newSimplexTable[i][realJ] = positionValue;
					}

					// Add solution variable

					if (i == 0) {
						newSolutionVariables.add(createVariable(solutionVariable, realJ));
					}
				} else {
					newSimplexTable[i][realJ] = positionValue;
				}

				realJ += 1;
			}
		}

		// Gaussian Elimination

		for (int index = 0; index < newSolutionVariables.size(); index++) {
			SolutionVariable solutionVariable = newSolutionVariables.get(index);
			if (solutionVariable.isBasic) {
				BigDecimal coefficientValue = newSimplexTable[0][solutionVariable.index];
				if (coefficientValue.compareTo(ZERO) != 0) {
					BigDecimal normalizer = coefficientValue.negate(MATH_CONTEXT);
					for (int j = 0; j < newSimplexTable[solutionVariable.tableLine].length; j++) {
						BigDecimal value = newSimplexTable[solutionVariable.tableLine][j];
						newSimplexTable[0][j] = (normalizer.multiply(value, MATH_CONTEXT)).add(newSimplexTable[0][j], MATH_CONTEXT);
					}
				}
			}
		}

		if (firstPhaseProblem.isDual) {
			return new DualTabularSolution(newSimplexTable, newSolutionVariables, INVERTED_MINIMIZATION);
		}
		return new PrimalTabularSolution(newSimplexTable, newSolutionVariables, INVERTED_MINIMIZATION);
	}
}
