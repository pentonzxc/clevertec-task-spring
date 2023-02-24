package com.nikolai.parserTests;

import com.nikolai.model.Receipt;
import com.nikolai.parser.*;
import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class ReceiptParserProviderTest {

    @Mock
    ProductService productService;

    @Mock
    DiscountCardService cardService;

    static ParserProvider<Receipt> provider;

    @BeforeAll
    public static void global() {
        provider = new ReceiptParserProvider();
    }


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenRegisterGenericParser_andGenericReceiptNotInstanceReceiptParserClass_thenThrowClassCastException() {
        var receiptParser = new Parser<Receipt>() {
            @Override
            public Receipt parse(String text) throws RuntimeException {
                return null;
            }
        };

        Assertions.assertThrows(ClassCastException.class, () -> provider.register(List.of(receiptParser)));
    }

    @Test
    public void whenRegisterGenericParser_andGenericReceiptInstanceReceiptParserClass_thenNotThrowException() {
        var fileReceiptParser = new FileReceiptParser(cardService, productService);

        var anyReceiptParser = new ReceiptParser(cardService, productService) {
            @Override
            protected String prepareText(String text) throws RuntimeException {
                return null;
            }
        };

        var commandLineReceiptParser = new CommandLineReceiptParser(cardService, productService);

        Assertions.assertDoesNotThrow(() -> provider.register(List.of(fileReceiptParser, anyReceiptParser, commandLineReceiptParser)));
    }

}
