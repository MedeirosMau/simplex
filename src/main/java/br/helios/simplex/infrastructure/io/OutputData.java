package br.helios.simplex.infrastructure.io;

import static br.helios.simplex.domain.problem.Objective.INVERTED_MAXIMIZATION;
import static br.helios.simplex.domain.problem.Objective.INVERTED_MINIMIZATION;
import static java.lang.String.format;

import java.math.BigDecimal;
import java.sql.Timestamp;

import br.helios.simplex.domain.tabularsolution.TabularSolution;
import br.helios.simplex.infrastructure.util.MathContextUtil;

public class OutputData {

	public static final String INFINITY = "INFINITY";

	private String message;
	private boolean newLine;
	private boolean timestamp;

	private OutputData(String message, boolean timestamp) {
		this.message = message;
		this.timestamp = timestamp;
	}

	public static OutputData message(TabularSolution tabularSolution) {
		StringBuilder builder = new StringBuilder();
		BigDecimal[][] simplexTable = tabularSolution.simplexTable;
		for (int i = 0; i < simplexTable.length; i++) {
			builder.append("(" + i + ") ");
			if (i == 0) {
				builder.append(
						fill(tabularSolution.objective == INVERTED_MINIMIZATION || tabularSolution.objective == INVERTED_MAXIMIZATION ? "-z " : "z"));
			} else {
				builder.append(fill(tabularSolution.getVariableByTableLine(i).name()));
			}
			for (int j = 0; j < simplexTable[i].length; j++) {
				builder.append(fill(simplexTable[i][j].round(MathContextUtil.MATH_CONTEXT_OUTPUT).toString()));
			}
			builder.append("\n");
		}
		return new OutputData(builder.toString(), false);
	}

	public static String fill(String originalMessage) {
		return format("%-20s", originalMessage);
	}

	public static OutputData message(String message) {
		return new OutputData(message, false);
	}

	public static OutputData timestampMessage(String message) {
		return new OutputData(message, true);
	}

	public OutputData line() {
		newLine = true;
		return this;
	}

	public void log() {
		String completeMessage = message;
		if (timestamp) {
			completeMessage = new Timestamp(System.currentTimeMillis()).toString() + " >> " + completeMessage;
		}
		if (newLine) {
			System.out.println(completeMessage);
		} else {
			System.out.print(completeMessage);
		}
	}
}
