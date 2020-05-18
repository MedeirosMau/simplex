package br.helios.simplex.domain.problem.parser;

import br.helios.simplex.domain.problem.variable.Variables;

public class ParserVariables {

	private static Variables variables = new Variables();

	public static Variables getVariables() {
		return variables;
	}

	public static void clear() {
		variables.clear();
	}
}
