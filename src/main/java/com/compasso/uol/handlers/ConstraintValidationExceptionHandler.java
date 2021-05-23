package com.compasso.uol.handlers;

import com.compasso.uol.handlers.custom.EntityErrorResponse;
import com.compasso.uol.handlers.custom.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ConstraintValidationExceptionHandler {

    /**
     * Exceções lançadas pelas anotações nos DTO's são capturadas e exibidas ao usuário
     *
     * @param exception exceção lançada
     * @return response contendo uma exibição mais clara e os erros da request
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<EntityErrorResponse> handleConstraintViolation(ConstraintViolationException exception) {

        List<StandardError> errors = getErrors(exception);
        EntityErrorResponse errorResponse = getErrorResponse(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * A partir da lista de erros monta o objeto de Entity Error
     *
     * @param errors lista de erros
     * @return objeto de EntityErrorResponse
     */
    private EntityErrorResponse getErrorResponse(List<StandardError> errors) {
        return new EntityErrorResponse("Erro ao salvar os dados.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                errors);
    }

    /**
     * Erros da requisição são mapeados para uma lista StandardError
     *
     * @param ex exceção lançada
     * @return lista de StandartError
     */
    private List<StandardError> getErrors(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream().map(
                error -> new StandardError(error.getMessage()))
                .collect(Collectors.toList());
    }
}
