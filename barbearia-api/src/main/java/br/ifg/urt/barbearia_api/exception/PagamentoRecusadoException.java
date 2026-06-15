package br.ifg.urt.barbearia_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PagamentoRecusadoException extends RuntimeException {

    public PagamentoRecusadoException(String mensagem) {
        super(mensagem);
    }
}