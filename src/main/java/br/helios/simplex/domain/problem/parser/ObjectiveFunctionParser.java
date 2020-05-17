package br.helios.simplex.domain.problem.parser;

import static br.helios.simplex.domain.problem.Operator.EQUAL;
import static br.helios.simplex.infrastructure.util.StringUtil.COMA;
import static br.helios.simplex.infrastructure.util.StringUtil.DOT;
import static br.helios.simplex.infrastructure.util.StringUtil.removeEmptyValues;

import java.util.List;

import br.helios.simplex.domain.problem.Objective;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.infrastructure.exception.InputDataException;

public class ObjectiveFunctionParser {

	private final TermParser termParser;
	private final ObjectiveParser objectiveParser;

	public ObjectiveFunctionParser() {
		this.termParser = new TermParser();
		this.objectiveParser = new ObjectiveParser();
	}

	public ObjectiveFunction parse(String inputData) {
		String dataNormalized = removeEmptyValues(inputData).replace(COMA, DOT);
		Objective objective = objectiveParser.parse(dataNormalized);
		List<Term> terms = termParser.parse(getTerms(dataNormalized));
		return new ObjectiveFunction(objective, terms);
	}

	private String getTerms(String data) {
		return data.substring(findInitialIndex(data));
	}

	private int findInitialIndex(String data) {
		int index = data.indexOf(EQUAL.operator());
		if (index < 0) {
			throw new InputDataException("Objective function is invalid!");
		}
		return index + 1;
	}
}
