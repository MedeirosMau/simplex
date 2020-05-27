package br.helios.simplex.domain.tabularsolution.initialsolution;

import static br.helios.simplex.domain.tabularsolution.SolutionVariable.createBasicVariable;
import static br.helios.simplex.domain.tabularsolution.SolutionVariable.createNonBasicVariable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.variable.Variable;
import br.helios.simplex.domain.problem.variable.Variables;
import br.helios.simplex.domain.tabularsolution.DualTabularSolution;
import br.helios.simplex.domain.tabularsolution.PrimalTabularSolution;
import br.helios.simplex.domain.tabularsolution.SolutionVariable;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

class SimpleProcessor implements InitialSolutionProcessor {

	private final InitialSimplexTableFactory initialSimplexTableFactory;

	public SimpleProcessor() {
		this.initialSimplexTableFactory = new InitialSimplexTableFactory();
	}

	@Override
	public TabularSolution createSolution(Problem artificialProblem) {
		BigDecimal[][] simplexTable = initialSimplexTableFactory.create(artificialProblem);
		List<Variable> problemVariables = artificialProblem.variables.getVariables();
		List<SolutionVariable> solutionVariables = new ArrayList<>(problemVariables.size());

		for (int i = 0; i < problemVariables.size(); i++) {
			Variable variable = problemVariables.get(i);
			solutionVariables.add(createSolutionVariable(variable, artificialProblem.variables));
		}

		if (artificialProblem.isDual) {
			return new DualTabularSolution(simplexTable, solutionVariables, artificialProblem.getObjective());
		}
		return new PrimalTabularSolution(simplexTable, solutionVariables, artificialProblem.getObjective());
	}

	private SolutionVariable createSolutionVariable(Variable variable, Variables variables) {
		int originalVariablesNum = variables.getOriginalVariables().size();
		if (variable.isOriginal) {
			return createNonBasicVariable(variable);
		} else {
			return createBasicVariable(variable, variable.id() - originalVariablesNum);
		}
	}
}
