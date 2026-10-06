package br.edu.iftm.ppw2.aula1.util.exception;

/**
 *
 * @author danilo
 */
public class ErroSistemaException extends Exception {

    public ErroSistemaException(String message) {
        super(message);
    }

    public ErroSistemaException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
