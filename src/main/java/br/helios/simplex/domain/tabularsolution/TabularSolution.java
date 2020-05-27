package br.helios.simplex.domain.tabularsolution;

import static br.helios.simplex.domain.problem.Objective.INVERTED_MINIMIZATION;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import br.helios.simplex.domain.problem.Objective;
import br.helios.simplex.infrastructure.util.MathContextUtil;

public abstract class TabularSolution {

	public final BigDecimal[][] simplexTable;
	public final List<SolutionVariable> variables;
	public final Objective objective;
	public boolean foundBestSolution;
	public final boolean isDual;

	public TabularSolution(BigDecimal[][] simplexTable, List<SolutionVariable> variables, Objective objective, boolean isDual) {
		this.simplexTable = simplexTable;
		this.variables = variables;
		this.objective = objective;
		this.isDual = isDual;
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
		if (objective == INVERTED_MINIMIZATION) {
			return value.negate(MathContextUtil.MATH_CONTEXT);
		}
		return value;

	}
}
