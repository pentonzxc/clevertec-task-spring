package com.nikolai.parser;

import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.model.Receipt;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

public class ReceiptParserProvider implements ParserProvider<Receipt> {
    private final Set<ReceiptParser> parsers = new HashSet<>();

    @Override
    public void register(List<? extends Parser<Receipt>> list) throws ClassCastException {
        for (Parser<Receipt> parser : list) {
            ReceiptParser receiptParser = (ReceiptParser) parser;
            parsers.add(receiptParser);
        }
    }

    @Override
    public Receipt parse(String str) throws UnsupportedPatternException, InputMismatchException {
        for (ReceiptParser parser : parsers) {
            try {
                return parser.parse(str);
            } catch (Exception ignored) {
            }
        }
        throw new UnsupportedPatternException();
    }
}
