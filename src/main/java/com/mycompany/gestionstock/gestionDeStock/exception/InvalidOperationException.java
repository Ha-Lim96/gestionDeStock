package com.mycompany.gestionstock.gestionDeStock.exception;

import lombok.Getter;

public class InvalidOperationException extends RuntimeException {

    @Getter
    private ErrorsCodes errorsCodes;

    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOperationException(String message, Throwable cause, ErrorsCodes errorsCodes) {
        super(message, cause);
        this.errorsCodes = errorsCodes;
    }

    public InvalidOperationException(String message, ErrorsCodes errorsCodes) {
        super(message);
        this.errorsCodes = errorsCodes;
    }



}
