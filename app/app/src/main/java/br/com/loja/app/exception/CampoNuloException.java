package br.com.loja.app.exception;

public class CampoNuloException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public CampoNuloException(String message) {
        super(message);
    }

    public CampoNuloException(String message, Throwable cause) {
        super(message, cause);
    }

}
