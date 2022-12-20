package com.nikolai;

import com.nikolai.decorator.StandardDiscountCard;
import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.parser.ReceiptParser;
import com.nikolai.storage.DiscountCardStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CommandLineReceiptParserTest {
    private static ReceiptParser cliReceiptParser;

    @BeforeAll
    public static void setUp() {
        cliReceiptParser = new com.nikolai.parser.CommandLineReceiptParser();
    }


    @Test
    public void givenCorrectInputCommandLine_thenReturnReceipt() {
        String withoutCard = "2-3 1-5 2-1";
        String withCard = "2-3 1-5 2-1 card-1234";
        String onlyCard = "card-1234";
        String oneProduct = "1-5";
        var discountCard = new StandardDiscountCard();
        discountCard.setId(5);
        discountCard.setCode(1234);
        DiscountCardStorage.add(discountCard.getCode(), discountCard);


        Assertions.assertDoesNotThrow(() -> {
            var receipt = cliReceiptParser.parse(withoutCard);
            int expectedSize = 2;

            Assertions.assertEquals(expectedSize, receipt.getOrdersCount());
            Assertions.assertEquals(4, receipt.get(2).getQuantity());
            Assertions.assertEquals(5, receipt.get(1).getQuantity());
        });

        Assertions.assertDoesNotThrow(() -> {
            var receipt = cliReceiptParser.parse(withCard);
            int expectedSize = 2;

            Assertions.assertEquals(discountCard, receipt.getDiscountCard());
            Assertions.assertEquals(expectedSize, receipt.getOrdersCount());
            Assertions.assertEquals(4, receipt.get(2).getQuantity());
            Assertions.assertEquals(5, receipt.get(1).getQuantity());
        });

        Assertions.assertDoesNotThrow(() -> {
            var receipt = cliReceiptParser.parse(onlyCard);
            int expectedSize = 0;

            Assertions.assertEquals(discountCard, receipt.getDiscountCard());
            Assertions.assertEquals(expectedSize, receipt.getOrdersCount());
        });

        Assertions.assertDoesNotThrow(() -> {
            var receipt = cliReceiptParser.parse(oneProduct);
            int expectedSize = 1;

            Assertions.assertEquals(5, receipt.get(1).getQuantity());
            Assertions.assertEquals(expectedSize, receipt.getOrdersCount());
        });
    }

    @Test
    public void givenMismatchInputCommandLine_thenReturnUnknownPatternException() {
        String emptyLine = "";
        String orderWithoutQuantity = "1- 2-5 card-1222";
        String orderWithZeroQuantity = "1-0";
        String orderWithoutId = "-2 2-3 card-1222";
        String invalidCardFormat1 = "card-123";
        String invalidCardFormat2 = "card-12344";
        String invalidCardFormat3 = "card-1234a";
        String invalidCardFormat4 = "card1234";

        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(emptyLine));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(orderWithoutId));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(orderWithoutQuantity));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(orderWithZeroQuantity));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(invalidCardFormat1));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(invalidCardFormat2));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(invalidCardFormat3));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(invalidCardFormat4));
    }


}
