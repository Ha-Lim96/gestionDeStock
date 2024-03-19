package com.mycompany.gestionstock.gestionDeStock.exception;

import lombok.Getter;

public class EntityNotFoundException extends RuntimeException {
    @Getter
    private ErrorsCodes errorsCodes;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String message, Throwable cause, ErrorsCodes errorsCodes) {
        super(message, cause);
        this.errorsCodes = errorsCodes;
    }

    public EntityNotFoundException(String message, ErrorsCodes errorsCodes) {
        super(message);
        this.errorsCodes = errorsCodes;
    }

}
