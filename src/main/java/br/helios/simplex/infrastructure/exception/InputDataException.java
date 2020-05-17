package br.helios.simplex.infrastructure.exception;

public class InputDataException extends RuntimeException {

	private static final long serialVersionUID = -4418335656070413156L;

	public InputDataException(String mensagem, Throwable e) {
		super(mensagem, e);
	}

	public InputDataException(String mensagem) {
		super(mensagem);
	}
}
