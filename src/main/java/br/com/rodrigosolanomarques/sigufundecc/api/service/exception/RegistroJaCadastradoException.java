package br.com.rodrigosolanomarques.sigufundecc.api.service.exception;

public class RegistroJaCadastradoException extends RuntimeException {

    public RegistroJaCadastradoException() {
    }

    public RegistroJaCadastradoException(String message) {
        super(message);
    }
}
