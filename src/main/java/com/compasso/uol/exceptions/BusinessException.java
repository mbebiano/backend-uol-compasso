package com.compasso.uol.exceptions;

/**
 * Exceção lançadas para erros relacionados a regras de negócio
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
