package br.helios.simplex.infrastructure.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import br.helios.simplex.infrastructure.exception.InputDataException;

public class InputData {

	public static final String INPUT_OK = "... ok";

	private static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

	public static String aguardarMensagem() {
		try {
			return buffer.readLine();
		} catch (IOException e) {
			throw new InputDataException("Erro na entrada de dados.", e);
		}
	}
}
