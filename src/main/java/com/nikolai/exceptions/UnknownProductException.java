package com.nikolai.exceptions;

public class UnknownProductException extends RuntimeException {
    public UnknownProductException() {
        super("Unknown product");
    }

    public UnknownProductException(String message) {
        super(message);
    }
}
