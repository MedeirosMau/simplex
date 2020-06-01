package br.helios.simplex.domain.sensitivity;

import static br.helios.simplex.infrastructure.io.OutputData.message;

import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.tabularsolution.TabularSolution;

public class SensitivityAnalysisService {

	private final RightHandSideAnalysis rightHandSideAnalysis;

	public SensitivityAnalysisService() {
		this.rightHandSideAnalysis = new RightHandSideAnalysis();
	}

	public void analyse(TabularSolution tabularSolution, Problem problem) {
		message("\n\n----------------------------------------------").line().log();
		message("    RANGES IN WHICH THE BASIS IS UNCHANGED    ").line().log();
		message("----------------------------------------------").line().log();
		rightHandSideAnalysis.analyse(tabularSolution, problem);
	}
}
