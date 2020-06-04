package br.helios.simplex.domain.dualproblem;

import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.tabularsolution.SolutionVariable;

public class DualDataTable {

	public List<DualData> duals = new ArrayList<>();

	public void add(DualData dual) {
		duals.add(dual);
	}

	public DualData getDualWithSolutionVariable(SolutionVariable solutionVariable) {
		for (DualData dual : duals) {
			if (dual.solutionVariable.equals(solutionVariable)) {
				return dual;
			}
		}
		return null;
	}

	public DualData getDualWithVariableName(String variableName) {
		for (DualData dual : duals) {
			if (dual.solutionVariable.variable.name.equalsIgnoreCase(variableName)) {
				return dual;
			}
		}
		return null;
	}

	public int size() {
		return duals.size();
	}
}
