package com.compasso.uol.exceptions;

/**
 * Exceçõo lançada para um recurso não encontrado
 */

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable causa) {
        super(message, causa);
    }
}
