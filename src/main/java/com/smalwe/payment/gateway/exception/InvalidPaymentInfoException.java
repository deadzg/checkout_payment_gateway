package com.smalwe.payment.gateway.exception;

public class InvalidPaymentInfoException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Invalid payment information";

    public InvalidPaymentInfoException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidPaymentInfoException(String exception) {
        super(exception);
    }
}
