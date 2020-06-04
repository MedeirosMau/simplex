package br.helios.simplex.domain.tabularsolution.initialsolution;

import static br.helios.simplex.domain.tabularsolution.SolutionVariable.createBasicVariable;
import static br.helios.simplex.domain.tabularsolution.SolutionVariable.createNonBasicVariable;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.domain.tabularsolution.PrimalTabularSolution;
import br.helios.simplex.domain.tabularsolution.SolutionVariable;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

public class CreateFirstPhaseInitialSolutionService implements InitialSolutionProcessor {

	private final InitialSimplexTableFactory initialSimplexTableFactory;

	public CreateFirstPhaseInitialSolutionService() {
		this.initialSimplexTableFactory = new InitialSimplexTableFactory();
	}

	@Override
	public TabularSolution createSolution(Problem artificialProblem) {
		BigDecimal[][] simplexTable = initialSimplexTableFactory.create(artificialProblem);
		List<Variable> problemVariables = artificialProblem.variables.getVariables();
		List<SolutionVariable> solutionVariables = new ArrayList<>(problemVariables.size());

		for (int i = 0; i < problemVariables.size(); i++) {
			Variable variable = problemVariables.get(i);
			solutionVariables.add(createSolutionVariable(variable, artificialProblem));
		}

		return new PrimalTabularSolution(simplexTable, solutionVariables, artificialProblem.getObjective(), artificialProblem);
	}

	private SolutionVariable createSolutionVariable(Variable variable, Problem artificialProblem) {
		Term term = artificialProblem.getObjectiveFunction().getTermByVariable(variable);
		if (term.getCoefficient().compareTo(ZERO) != 0 || term.getVariable().isOriginal) {
			return createNonBasicVariable(variable);
		} else {
			return createBasicVariable(variable, variable.constraintOrder);
		}
	}
}