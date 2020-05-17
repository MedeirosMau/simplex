package br.helios.simplex.domain.problem;

import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.domain.problem.parser.ConstraintParser;
import br.helios.simplex.domain.problem.parser.ObjectiveFunctionParser;

public class ProblemTestBuilder {

	private String inputObjectiveFunction;
	private List<String> inputConstraints = new ArrayList<String>();

	private ProblemTestBuilder() {
		// default constructor
	}

	public static ProblemTestBuilder create() {
		return new ProblemTestBuilder();
	}

	public static ProblemTestBuilder create(String inputObjectiveFunction, List<String> inputConstraints) {
		return new ProblemTestBuilder().objectiveFunction(inputObjectiveFunction).constraints(inputConstraints);
	}

	public ProblemTestBuilder objectiveFunction(String inputData) {
		this.inputObjectiveFunction = inputData;
		return this;
	}

	public ProblemTestBuilder constraints(List<String> inputData) {
		this.inputConstraints = inputData;
		return this;
	}

	public ProblemTestBuilder addConstraint(String inputData) {
		this.inputConstraints.add(inputData);
		return this;
	}

	public Problem build() {
		Variables.clear();
		ObjectiveFunction objectiveFunction = new ObjectiveFunctionParser().parse(inputObjectiveFunction);
		List<Constraint> constraints = new ArrayList<Constraint>();
		for (String inputConstraint : inputConstraints) {
			constraints.add(new ConstraintParser().parse(inputConstraint, constraints.size() + 1));
		}
		return new Problem(objectiveFunction, constraints);
	}
}
