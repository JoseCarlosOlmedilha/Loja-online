package br.com.loja.app.exception;

public class DadosIvalidosException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DadosIvalidosException(String message) {
        super(message);
    }

    public DadosIvalidosException(String message, Throwable cause) {
        super(message, cause);
    }
}
