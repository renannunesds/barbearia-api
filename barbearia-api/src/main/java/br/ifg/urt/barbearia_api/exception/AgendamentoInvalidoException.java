package br.ifg.urt.barbearia_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AgendamentoInvalidoException extends RuntimeException {

    public AgendamentoInvalidoException(String mensagem) {
        super(mensagem);
    }
}