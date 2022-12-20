package com.nikolai.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReceiptParser extends ReceiptParser {

    @Override
    protected String prepareText(String fileName) throws IOException {
        StringBuilder str = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                str.append(line.replaceAll("\r\n|\n|\r", ""));
            }
            return str.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
