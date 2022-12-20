package com.nikolai.parser;

public class CommandLineReceiptParser extends ReceiptParser {
    @Override
    protected String prepareText(String text) {
        return text.trim();
    }
}
