package org.infnet.guildaApiTP1.advice;

import jakarta.validation.ConstraintViolationException;

import org.infnet.guildaApiTP1.dto.ErroResponse;
import org.infnet.guildaApiTP1.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;


@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResponse> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse("Recurso não encontrado", List.of(ex.getMessage())));
    }

    //separar oos handlers depois
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class, IllegalArgumentException.class, HandlerMethodValidationException.class})
    public ResponseEntity<ErroResponse> handleBadRequest(Exception ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse("Solicitação inválida",List.of(ex.getMessage())));
    }
}
