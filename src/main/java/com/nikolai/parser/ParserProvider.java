package com.nikolai.parser;

import com.nikolai.exceptions.UnsupportedPatternException;

import java.util.InputMismatchException;
import java.util.List;

public interface ParserProvider<T> {
    void register(List<? extends Parser<T>> list) throws ClassCastException;

    T parse(String str) throws UnsupportedPatternException, InputMismatchException;

}
