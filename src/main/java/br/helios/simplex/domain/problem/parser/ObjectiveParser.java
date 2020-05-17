package br.helios.simplex.domain.problem.parser;

import static br.helios.simplex.domain.problem.Objective.MAXIMIZATION;
import static br.helios.simplex.domain.problem.Objective.MINIMIZATION;

import br.helios.simplex.domain.problem.Objective;
import br.helios.simplex.infrastructure.exception.InputDataException;

class ObjectiveParser {

	public Objective parse(String data) {
		if (data.toLowerCase().indexOf(MAXIMIZATION.description().toLowerCase()) >= 0) {
			return MAXIMIZATION;
		} else if (data.toLowerCase().indexOf(MINIMIZATION.description().toLowerCase()) >= 0) {
			return MINIMIZATION;
		}
		throw new InputDataException("Function objective not found");
	}
}
