package com.nikolai.parser;

import com.nikolai.exceptions.UnsupportedPatternException;

import java.io.IOException;

public interface Parser<R> {
    R parse(String text) throws RuntimeException;
}
