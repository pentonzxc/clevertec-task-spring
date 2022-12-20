package com.nikolai.exceptions;

public class UnknownDiscountCardException extends RuntimeException {
    public UnknownDiscountCardException() {
        super("Unknown card");
    }

    public UnknownDiscountCardException(String message) {
        super(message);
    }
}
