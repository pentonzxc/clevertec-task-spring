package com.nikolai.exceptions;

public class UnsupportedPatternException extends RuntimeException {
    public UnsupportedPatternException() {
        super("Unknown pattern");
    }

    public UnsupportedPatternException(String message) {
        super(message);
    }
}
