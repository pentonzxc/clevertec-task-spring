package com.nikolai.parserTests;

import com.nikolai.model.Receipt;
import com.nikolai.parser.*;
import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ReceiptParserProviderTest {

    @Mock
    private ProductService productService;

    @Mock
    private DiscountCardService cardService;

    private static ParserProvider<Receipt> provider;

    @BeforeAll
    static void global() {
        provider = new ReceiptParserProvider();
    }


    @Test
    void whenRegisterGenericParser_andGenericReceiptNotInstanceReceiptParserClass_thenThrowClassCastException() {
        var receiptParser = new Parser<Receipt>() {
            @Override
            public Receipt parse(String text) throws RuntimeException {
                return null;
            }
        };

        Assertions.assertThrows(ClassCastException.class, () -> provider.register(List.of(receiptParser)));
    }

    @Test
    void whenRegisterGenericParser_andGenericReceiptInstanceReceiptParserClass_thenNotThrowException() {
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
