package br.helios.simplex.domain.dualproblem;

import java.math.BigDecimal;

import br.helios.simplex.domain.tabularsolution.SolutionVariable;

public class DualData {

	public final SolutionVariable solutionVariable;
	public final BigDecimal slackValue;
	public final BigDecimal dualValue;

	public DualData(SolutionVariable solutionVariable, BigDecimal slackValue, BigDecimal dualValue) {
		this.solutionVariable = solutionVariable;
		this.slackValue = slackValue;
		this.dualValue = dualValue;
	}

}
