package br.ifg.urt.barbearia_api.exception.handler;

import java.time.LocalDateTime;
import br.ifg.urt.barbearia_api.exception.ExceptionResponse;
import br.ifg.urt.barbearia_api.exception.ResourceNotFoundException;
import br.ifg.urt.barbearia_api.exception.AgendamentoInvalidoException;
import br.ifg.urt.barbearia_api.exception.PagamentoRecusadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 1. Captura erros de Recursos Não Encontrados (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(
            Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    // 2. Captura regras de negócio inválidas e devolve a mensagem real (400 Bad Request)
    @ExceptionHandler({AgendamentoInvalidoException.class, PagamentoRecusadoException.class})
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(
            Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                ex.getMessage(), // Aqui vai mostrar a mensagem real: "Este barbeiro já possui um agendamento..."
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    // 3. Captura qualquer outro erro inesperado do sistema (500)
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(
            Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                "Ocorreu um erro interno no servidor.",
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}