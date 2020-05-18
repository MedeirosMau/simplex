package br.helios.simplex;

import static br.helios.simplex.domain.problem.parser.ParserVariables.getVariables;
import static br.helios.simplex.infrastructure.io.InputData.INPUT_OK;
import static br.helios.simplex.infrastructure.io.InputData.aguardarMensagem;
import static br.helios.simplex.infrastructure.io.OutputData.message;

import java.util.ArrayList;
import java.util.List;

import br.helios.simplex.application.TabularSolverApplicationService;
import br.helios.simplex.domain.problem.Constraint;
import br.helios.simplex.domain.problem.ObjectiveFunction;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.problem.parser.ConstraintParser;
import br.helios.simplex.domain.problem.parser.ObjectiveFunctionParser;

public class Simplex {

	private static final String SOLVE_INPUT = "solve";

	public static void main(String[] args) {
		ObjectiveFunctionParser objectiveFunctionParser = new ObjectiveFunctionParser();
		ConstraintParser constraintParser = new ConstraintParser();

		message("Objective function: ").log();
		String inputObjectiveFunction = aguardarMensagem();
		ObjectiveFunction objectiveFunction = objectiveFunctionParser.parse(inputObjectiveFunction);
		message(INPUT_OK).line().log();

		List<Constraint> constraints = new ArrayList<Constraint>();
		for (int i = 1;; i++) {
			message("New constraint: ").log();
			String input = aguardarMensagem();

			if (input.equalsIgnoreCase(SOLVE_INPUT)) {
				break;
			} else {
				constraints.add(constraintParser.parse(input, i));
				message(INPUT_OK).line().log();
			}
		}

		Problem problem = new Problem(objectiveFunction, constraints, getVariables());

		message(" -- PROBLEM -- ").line().log();
		message(problem.toString()).log();

		new TabularSolverApplicationService().solve(problem);

	}

}
