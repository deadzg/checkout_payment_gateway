package com.smalwe.payment.gateway.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Payment Not Found";

    public ResourceNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ResourceNotFoundException(String exception) {
        super(exception);
    }
}
