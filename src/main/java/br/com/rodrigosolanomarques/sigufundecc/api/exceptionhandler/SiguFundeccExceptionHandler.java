package br.com.rodrigosolanomarques.sigufundecc.api.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SiguFundeccExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        Erro erro = formatarErro(exception);

        return handleExceptionInternal(exception, erro, headers, HttpStatus.BAD_REQUEST, request);
    }

    private Erro formatarErro(HttpMessageNotReadableException exception) {

        String mensagemUsuario = formatarMensagemParaUsuario();

        String mensagemDesenvolvedor = formatarMensagemParaDesenvolvedor(exception);

        return new Erro(mensagemUsuario, mensagemDesenvolvedor);
    }

    private String formatarMensagemParaUsuario() {
        return messageSource.getMessage(
                "mensagem.invalida",
                null,
                LocaleContextHolder.getLocale());
    }

    private String formatarMensagemParaDesenvolvedor(HttpMessageNotReadableException exception) {
        return exception.getCause().toString();
    }

    public static class Erro {

        private final String mensagemUsuario;
        private final String mensagemDesenvolvedor;

        public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
            this.mensagemUsuario = mensagemUsuario;
            this.mensagemDesenvolvedor = mensagemDesenvolvedor;
        }

        public String getMensagemUsuario() {
            return mensagemUsuario;
        }

        public String getMensagemDesenvolvedor() {
            return mensagemDesenvolvedor;
        }
    }
}
