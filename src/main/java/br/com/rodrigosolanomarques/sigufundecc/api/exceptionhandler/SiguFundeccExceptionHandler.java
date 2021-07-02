package br.com.rodrigosolanomarques.sigufundecc.api.exceptionhandler;

import br.com.rodrigosolanomarques.sigufundecc.api.service.exception.RegistroJaCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        Erro erro = formatarErro(exception, "mensagem.invalida");
        List<Erro> erros = Collections.singletonList(erro);
        return handleExceptionInternal(exception, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<Erro> erros = criarListaDeErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAcessException(EmptyResultDataAccessException exception, WebRequest request) {
        Erro erro = formatarErro(exception, "recurso.nao-encontrado");
        List<Erro> erros = Collections.singletonList(erro);
        return handleExceptionInternal(exception, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(RegistroJaCadastradoException.class)
    public ResponseEntity<Object> handleCpfJaCadastradoException(RegistroJaCadastradoException exception, WebRequest request) {
        Erro erro = formatarErro(exception, "registro.ja-cadastrado");
        List<Erro> erros = Collections.singletonList(erro);
        return handleExceptionInternal(exception, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleRegistroSendoUsadoException(DataIntegrityViolationException exception, WebRequest request) {
        Erro erro = formatarErro(exception, "registro.sendo-usado");
        List<Erro> erros = Collections.singletonList(erro);
        return handleExceptionInternal(exception, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private List<Erro> criarListaDeErros(BindingResult bindingResult) {
        List<Erro> erros = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String mesagemDesenvolvedor = fieldError.toString();

            erros.add(new Erro(mensagemUsuario, mesagemDesenvolvedor));
        });

        return erros;
    }

    private Erro formatarErro(Throwable exception, String msg) {

        String mensagemUsuario = formatarMensagemParaUsuario(msg);

        String mensagemDesenvolvedor = formatarMensagemParaDesenvolvedor(exception);

        return new Erro(mensagemUsuario, mensagemDesenvolvedor);
    }

    private String formatarMensagemParaUsuario(String msg) {
        return messageSource.getMessage(
                msg,
                null,
                LocaleContextHolder.getLocale());
    }

    private String formatarMensagemParaDesenvolvedor(Throwable exception) {

        if (exception.getCause() == null) {
            return exception.toString();
        }

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
