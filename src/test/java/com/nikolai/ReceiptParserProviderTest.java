package com.nikolai;

import com.nikolai.model.Receipt;
import com.nikolai.parser.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ReceiptParserProviderTest {
    private ParserProvider<Receipt> provider = new ReceiptParserProvider();


    @Test
    public void whenRegisterGenericParserReceipt_butNotReceiptParserClass_thenReturnClassCastException() {
        var receiptParser = new Parser<Receipt>() {
            @Override
            public Receipt parse(String text) throws RuntimeException {
                return null;
            }
        };

        Assertions.assertThrows(ClassCastException.class, () -> provider.register(List.of(receiptParser)));
    }

    @Test
    public void whenRegisterGenericParserReceipt_butItReceiptParserClass_thenReturnVoid() {
        var fileReceiptParser = new FileReceiptParser();

        var anyReceiptParser = new ReceiptParser() {
            @Override
            protected String prepareText(String text) throws Exception {
                return null;
            }
        };

        var commandLineReceiptParser = new CommandLineReceiptParser();

        Assertions.assertDoesNotThrow(() -> {
           provider.register(List.of(fileReceiptParser , anyReceiptParser , commandLineReceiptParser));
        });
    }

}
