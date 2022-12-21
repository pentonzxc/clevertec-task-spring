package com.nikolai.parser;

import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Component("fileReceiptParser")
public class FileReceiptParser extends ReceiptParser {

    @Autowired
    public FileReceiptParser(DiscountCardService cardService, ProductService productService) {
        super(cardService, productService);
    }

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
