package br.helios.simplex.application;

import static br.helios.simplex.infrastructure.io.OutputData.message;

import br.helios.simplex.domain.artificialproblem.CreateArtificialProblemService;
import br.helios.simplex.domain.dualproblem.CreateDualProblemService;
import br.helios.simplex.domain.dualproblem.CreateDualTableService;
import br.helios.simplex.domain.problem.Problem;
import br.helios.simplex.domain.sensitivity.SensitivityAnalysisService;
import br.helios.simplex.domain.tabularsolution.TabularSolution;
import br.helios.simplex.domain.tabularsolution.TabularSolverService;

public class TabularSolverApplicationService {

	private final CreateArtificialProblemService createArtificialProblemService;
	private final TabularSolverService tabularSolverService;
	private final CreateDualProblemService createDualProblemService;
	private final SensitivityAnalysisService sensitivityAnalysisService;
	private final CreateDualTableService createDualTableService;

	public TabularSolverApplicationService() {
		this.createArtificialProblemService = new CreateArtificialProblemService();
		this.tabularSolverService = new TabularSolverService();
		this.createDualProblemService = new CreateDualProblemService();
		this.sensitivityAnalysisService = new SensitivityAnalysisService();
		this.createDualTableService = new CreateDualTableService();
	}

	// TODO: dual
	// TODO: sensitivity analysis
	/*
	 * dual s� mantar o dual e pegar a solu��o dual do primal do tableau final e
	 * tamb�m saber interepretar a� recursos escassos, abundantes e vari�veis duais
	 * associadas e analise de sensibilidade
	 */

	/*
	 * an�lise de sensibilidade s� precisa dos RHS mas se tu fizer dos coef da FO q
	 * eu nem expliquei mas tem nos slides e foi tirado do H&L a� vai desmoralizar o
	 * resto e � f�cil tu fechar pegar o dual do tableau � trivial saber o lance de
	 * escasso e abundadante basta ver o dual e tamb�m vari�veis de folga podem dar
	 * uma pista a� interpreta��o econ�mica
	 */
	public TabularSolution solve(Problem problem) {
		message("-- ORIGINAL PROBLEM -- ").log();
		message(problem.toString()).line().log();
		Problem artificialProblem = createArtificialProblemService.create(problem);
		message("-- ARTIFICIAL PROBLEM -- ").log();
		message(artificialProblem.toString()).line().log();
		TabularSolution tabularSolution = tabularSolverService.solve(artificialProblem);
		tabularSolution.dualDataTable = createDualTableService.createDualTable(tabularSolution, artificialProblem);
		sensitivityAnalysisService.analyse(tabularSolution, artificialProblem);
		return tabularSolution;
	}

	public TabularSolution solveDual(Problem problem) {
		message("-- ORIGINAL PROBLEM -- ").log();
		message(problem.toString()).line().log();
		Problem dualProblem = createDualProblemService.create(problem);
		message("-- DUAL PROBLEM -- ").log();
		message(dualProblem.toString()).line().log();
		message("-- ARTIFICIAL PROBLEM -- ").log();
		Problem artificialProblem = createArtificialProblemService.create(dualProblem);
		message(artificialProblem.toString()).line().log();
		return tabularSolverService.solve(artificialProblem);
	}

}
