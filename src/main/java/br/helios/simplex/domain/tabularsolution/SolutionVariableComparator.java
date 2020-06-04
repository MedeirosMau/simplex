package br.helios.simplex.domain.tabularsolution;

import java.util.Comparator;

public class SolutionVariableComparator implements Comparator<SolutionVariable> {

	@Override
	public int compare(SolutionVariable s1, SolutionVariable s2) {
		if (s1.tableLine < s2.tableLine) {
			return -1;
		}
		return 1;
	}

}
