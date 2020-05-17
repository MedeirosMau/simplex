package br.helios.simplex.domain.problem;

import java.util.List;

public class TermTestHelper {

	public static boolean contains(List<Term> terms, Term expectedTerm) {
		for (Term term : terms) {
			if (term.getCoefficient() == expectedTerm.getCoefficient() && term.getVariable().name.equalsIgnoreCase(expectedTerm.getVariable().name)) {
				return true;
			}
		}
		return false;
	}
}
