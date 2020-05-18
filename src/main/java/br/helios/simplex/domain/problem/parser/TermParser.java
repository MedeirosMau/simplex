package br.helios.simplex.domain.problem.parser;

import static br.helios.simplex.domain.problem.Term.createTerm;
import static br.helios.simplex.domain.problem.parser.ParserVariables.getVariables;
import static br.helios.simplex.infrastructure.util.StringUtil.COMA;
import static br.helios.simplex.infrastructure.util.StringUtil.DOT;
import static br.helios.simplex.infrastructure.util.StringUtil.NEGATIVE;
import static br.helios.simplex.infrastructure.util.StringUtil.POSITIVE;
import static java.lang.Character.isDigit;
import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.Term;
import br.helios.simplex.domain.problem.variable.CreateVariableService;
import br.helios.simplex.domain.problem.variable.Variable;

class TermParser {

	private final CreateVariableService createVariableService;

	public TermParser() {
		this.createVariableService = new CreateVariableService();
	}

	public List<Term> parse(String data) {
		StringBuffer coefficientBuffer = new StringBuffer();
		StringBuffer variableBuffer = new StringBuffer();

		boolean isProcessingCoefficient = true;

		List<Term> terms = new ArrayList<Term>();

		for (int i = 0; i < data.length(); i++) {
			Character charValue = data.charAt(i);
			String value = valueOf(charValue);

			if (isProcessingCoefficient) {
				if (isDigit(charValue) || value.equals(NEGATIVE) || value.equals(COMA) || value.equals(DOT)) {
					coefficientBuffer.append(charValue);
				} else {
					isProcessingCoefficient = false;
					variableBuffer.append(charValue);
				}
			} else {
				if (value.equals(POSITIVE) || value.equals(NEGATIVE)) {
					terms.add(createNewTerm(coefficientBuffer, variableBuffer));
					coefficientBuffer = new StringBuffer();
					variableBuffer = new StringBuffer();
					isProcessingCoefficient = true;
					coefficientBuffer.append(charValue);
				} else {
					variableBuffer.append(charValue);
				}
			}
		}

		terms.add(createNewTerm(coefficientBuffer, variableBuffer));
		return terms;
	}

	private Term createNewTerm(StringBuffer coefficientBuffer, StringBuffer variableBuffer) {
		Variable variable = createVariableService.createOriginalVariable(variableBuffer.toString(), getVariables());
		return createTerm(coefficientValue(coefficientBuffer.toString()), variable);
	}

	private Double coefficientValue(String value) {
		if (value.length() > 0) {
			if (value.length() == 1) {
				if (value.equals("+")) {
					return new Double(1);
				} else if (value.equals("-")) {
					return new Double(-1);
				}
			}
			String valueNormalized = value.replace("+", "").trim();
			return new Double(valueNormalized);
		}
		return new Double(1);
	}

}
