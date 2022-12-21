package com.nikolai.parserTests;

import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.parser.CommandLineReceiptParser;
import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class CommandLineReceiptParserTest {
    @InjectMocks
    private CommandLineReceiptParser cliReceiptParser;

    @Mock
    private ProductService productService;

    @Mock
    private DiscountCardService discountCardService;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void givenCorrectInputCommandLine_thenReturnReceipt() {
        String withoutCard = "2-3 1-5 2-1";
        String withCard = "2-3 1-5 2-1 card-1234";
        String oneProduct = "1-5";

        Mockito.when(discountCardService.findCardByCode(1234)).thenReturn(
                Optional.of(new StandardDiscountCard(1, 20, 1234))
        );

        var discountCard = discountCardService.findCardByCode(1234).get();

        Mockito.when(productService.findProductById(1)).thenReturn(
                Optional.of(new Product(1, 2D))
        );

        Mockito.when(productService.findProductById(2)).thenReturn(
                Optional.of(new Product(2, 5D))
        );


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
        String onlyCard = "card-1234";
        String invalidCardFormat1 = "card-123";
        String invalidCardFormat2 = "card-12344";
        String invalidCardFormat3 = "card-1234a";
        String invalidCardFormat4 = "card1234";

        Mockito.when(discountCardService.findCardByCode(1234)).thenReturn(
                Optional.of(new StandardDiscountCard(1, 20, 1234))
        );

        Mockito.when(productService.findProductById(1)).thenReturn(
                Optional.of(new Product(1, 2D))
        );

        Mockito.when(productService.findProductById(2)).thenReturn(
                Optional.of(new Product(2, 5D))
        );


        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(emptyLine));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(orderWithoutId));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(orderWithoutQuantity));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(orderWithZeroQuantity));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(invalidCardFormat1));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(invalidCardFormat2));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(onlyCard));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(invalidCardFormat3));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(invalidCardFormat4));
    }


}
