package br.helios.simplex.domain.problem;

public enum Objective {

	MAXIMIZATION("max"), MINIMIZATION("min"), INVERTED_MINIMIZATION("max(-)");

	private final String description;

	private Objective(String description) {
		this.description = description;
	}

	public String description() {
		return description;
	}
}
