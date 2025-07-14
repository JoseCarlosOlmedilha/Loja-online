package br.com.loja.app.exception;

public class DadosInvalidosException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DadosInvalidosException(String message) {
        super(message);
    }

    public DadosInvalidosException(String message, Throwable cause) {
        super(message, cause);
    }
}
