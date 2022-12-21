package com.nikolai.parser;

import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("cliReceiptParser")
public class CommandLineReceiptParser extends ReceiptParser {

    @Autowired
    public CommandLineReceiptParser(DiscountCardService cardService, ProductService productService) {
        super(cardService, productService);
    }

    @Override
    protected String prepareText(String text) {
        return text.trim();
    }
}
