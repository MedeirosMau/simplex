package br.helios.simplex.domain.tabularsolution.initialsolution;

import static br.helios.simplex.domain.problem.Variables.getOriginalVariables;
import static br.helios.simplex.domain.problem.Variables.getVariables;
import static br.helios.simplex.domain.tabularsolution.SolutionVariable.createBasicVariable;
import static br.helios.simplex.domain.tabularsolution.SolutionVariable.createNonBasicVariable;

import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.Variable;
import br.helios.simplex.domain.tabularsolution.SolutionVariable;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

class SimpleProcessor implements InitialSolutionProcessor {

	private final InitialSimplexTableFactory initialSimplexTableFactory;

	public SimpleProcessor() {
		this.initialSimplexTableFactory = new InitialSimplexTableFactory();
	}

	@Override
	public TabularSolution createSolution(Problem artificialProblem) {
		double[][] simplexTable = initialSimplexTableFactory.create(artificialProblem);
		List<Variable> problemVariables = getVariables();
		List<SolutionVariable> solutionVariables = new ArrayList<>(problemVariables.size());

		for (int i = 0; i < problemVariables.size(); i++) {
			Variable variable = problemVariables.get(i);
			solutionVariables.add(createSolutionVariable(variable));
		}

		return new TabularSolution(simplexTable, solutionVariables, artificialProblem.getObjective());
	}

	private SolutionVariable createSolutionVariable(Variable variable) {
		int originalVariablesNum = getOriginalVariables().size();
		if (variable.isOriginal) {
			return createNonBasicVariable(variable);
		} else {
			return createBasicVariable(variable, variable.id() - originalVariablesNum);
		}
	}
}
