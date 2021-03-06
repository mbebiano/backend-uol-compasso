package com.compasso.uol.handlers;

import com.compasso.uol.exceptions.BusinessException;
import com.compasso.uol.exceptions.ResourceNotFoundException;
import com.compasso.uol.handlers.custom.EntityErrorResponse;
import com.compasso.uol.handlers.custom.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERRO_NA_REQUISICAO = "Erro na requisição";

    /**
     * Captura execeções lançadas e exibe de forma mais clara
     *
     * @param exception exceção lançada
     * @return response contendo uma mensagem mais amigavél e os erros na request
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<EntityErrorResponse> handleBusinessException (BusinessException exception){
        List<StandardError> errors = new ArrayList<>();
        errors.add(new StandardError(exception.getLocalizedMessage()));
        EntityErrorResponse response = new EntityErrorResponse(
                ERRO_NA_REQUISICAO,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Captura execeções lançadas e exibe de forma mais clara
     *
     * @param exception exceção lançada
     * @return response contendo uma exibição mais clara e os erros da requestt
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<EntityErrorResponse> handleResourceNotFoundException (ResourceNotFoundException exception){
        List<StandardError> errors = new ArrayList<>();
        errors.add(new StandardError(exception.getLocalizedMessage()));
        EntityErrorResponse response = new EntityErrorResponse(
                ERRO_NA_REQUISICAO,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                errors);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

