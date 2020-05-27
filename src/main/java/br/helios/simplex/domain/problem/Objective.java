package br.helios.simplex.domain.problem;

public enum Objective {

	MAXIMIZATION("max"), MINIMIZATION("min"), INVERTED_MINIMIZATION("max(-)"), INVERTED_MAXIMIZATION("min(-)");

	private final String description;

	private Objective(String description) {
		this.description = description;
	}

	public String description() {
		return description;
	}

	public Objective invert() {
		if (this == MINIMIZATION) {
			return MAXIMIZATION;
		}
		return MINIMIZATION;
	}
}
