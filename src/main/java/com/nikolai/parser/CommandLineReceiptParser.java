package com.nikolai.parser;

import org.springframework.stereotype.Component;

@Component("cliReceiptParser")
public class CommandLineReceiptParser extends ReceiptParser {
    @Override
    protected String prepareText(String text) {
        return text.trim();
    }
}
