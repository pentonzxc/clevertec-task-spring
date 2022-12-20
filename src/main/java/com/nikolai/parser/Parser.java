package com.nikolai.parser;

public interface Parser<R> {
    R parse(String text) throws RuntimeException;
}
