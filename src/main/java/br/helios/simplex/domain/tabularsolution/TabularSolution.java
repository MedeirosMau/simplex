package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.domain.problem.Objective.INVERTED_MAXIMIZATION;
import static br.helios.simplex.domain.problem.Objective.INVERTED_MINIMIZATION;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.helios.simplex.domain.dualproblem.DualDataTable;
import br.helios.simplex.domain.problem.Objective;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.infrastructure.util.MathContextUtil;

public abstract class TabularSolution {

	public final BigDecimal[][] simplexTable;
	public final List<SolutionVariable> variables;
	public final Objective objective;
	public boolean foundBestSolution;
	public final Problem problem;
	public DualDataTable dualDataTable;

	public TabularSolution(BigDecimal[][] simplexTable, List<SolutionVariable> variables, Objective objective, Problem problem) {
		this.simplexTable = simplexTable;
		this.variables = variables;
		this.objective = objective;
		this.problem = problem;
	}

	public int getConstraintsNum() {
		return simplexTable.length - 1;
	}

	public int getOriginalVariablesNum() {
		int total = 0;
		for (SolutionVariable variable : variables) {
			if (variable.variable.isOriginal) {
				total++;
			}
		}
		return total;
	}

	public SolutionVariable getNonOriginalVariableFromConstraint(int constraintOrder) {
		for (SolutionVariable variable : variables) {
			if (variable.variable.constraintOrder == constraintOrder) {
				return variable;
			}
		}
		throw new IllegalArgumentException("No variables found");
	}

	public abstract SolutionVariable getBasicVariableCandidate(SolutionVariable nonBasicCandidateVariable);

	public abstract SolutionVariable getNonBasicVariableCandidate(SolutionVariable basicCandidateVariable);

	public abstract boolean isBestSolution();

	public SolutionVariable getVariableByTableLine(int tableLine) {
		for (SolutionVariable variable : variables) {
			if (variable.tableLine == tableLine) {
				return variable;
			}
		}
		throw new IllegalArgumentException("Invalid table line");
	}

	public SolutionVariable getVariableWithIndex(int index) {
		for (SolutionVariable solutionVariable : variables) {
			if (solutionVariable.index == index) {
				return solutionVariable;
			}
		}
		throw new IllegalArgumentException("Not a valid index");
	}

	public BigDecimal[][] copySimplexTable() {
		final BigDecimal[][] copy = new BigDecimal[simplexTable.length][];
		for (int i = 0; i < simplexTable.length; i++) {
			copy[i] = Arrays.copyOf(simplexTable[i], simplexTable[i].length);
		}
		return copy;
	}

	public BigDecimal getSolutionValue() {
		BigDecimal value = simplexTable[0][simplexTable[0].length - 1];
		if (objective == INVERTED_MINIMIZATION || objective == INVERTED_MAXIMIZATION) {
			return value.negate(MathContextUtil.MATH_CONTEXT);
		}
		return value;
	}

	public List<SolutionVariable> getBasicVariables() {
		List<SolutionVariable> basicVariables = new ArrayList<>();
		for (SolutionVariable variable : variables) {
			if (variable.isBasic) {
				basicVariables.add(variable);
			}
		}
		return basicVariables;
	}

	public List<SolutionVariable> getBasicVariablesOrderedByTableLine() {
		List<SolutionVariable> basicVariables = getBasicVariables();
		Collections.sort(basicVariables, new SolutionVariableComparator());
		return basicVariables;
	}

	public List<SolutionVariable> getNonBasicVariables() {
		List<SolutionVariable> nonBasicVariables = new ArrayList<>();
		for (SolutionVariable variable : variables) {
			if (!variable.isBasic) {
				nonBasicVariables.add(variable);
			}
		}
		return nonBasicVariables;
	}
}
