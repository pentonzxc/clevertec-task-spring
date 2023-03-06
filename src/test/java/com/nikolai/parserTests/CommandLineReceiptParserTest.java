package com.nikolai.parserTests;

import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.parser.CommandLineReceiptParser;
import com.nikolai.provider.ReceiptDataProvider;
import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CommandLineReceiptParserTest {
    @InjectMocks
    private CommandLineReceiptParser cliReceiptParser;

    @Mock
    private ProductService productService;

    @Mock
    private DiscountCardService discountCardService;


    @BeforeEach
    void init() {
        configStorage();
    }


    @ParameterizedTest(name = "input - {arguments}")
    @MethodSource("com.nikolai.provider.ReceiptDataProvider#receipts")
    void whenValidReceipt_thenNotThrowException(String receipt) {
        Assertions.assertDoesNotThrow(() -> cliReceiptParser.parse(receipt));
    }

    @ParameterizedTest(name = "input - {arguments}")
    @MethodSource("com.nikolai.provider.ReceiptDataProvider#invalidReceipts")
    void whenInvalidReceipt_thenThrowUnknownPatternException(String receipt) {
        Assertions.assertThrows(UnsupportedPatternException.class, () -> cliReceiptParser.parse(receipt));
    }

    @Test
    void whenReceiptWithCard_thenReceiptGetCard() {
        var input = ReceiptDataProvider.receipt();
        var discountCard = discountCardService.findCardByCode(1234).get();

        var receipt = cliReceiptParser.parse(input);

        Assertions.assertSame(receipt.getDiscountCard(), discountCard);
    }


    @ParameterizedTest
    @CsvSource({
            "2, 4",
            "1, 5",
    })
    void whenReceipt_checkProductsQuantity(int productId, int productQuantity) {
        var input = ReceiptDataProvider.receiptWithoutCard();
        var receipt = cliReceiptParser.parse(input);

        Assertions.assertEquals(receipt.get(productId).getQuantity(), productQuantity);
    }


    @ParameterizedTest
    @CsvSource({
            "2-3 1-5 2-1, 2"
    })
    void whenReceipt_checkOrdersCount(String receiptAsString, int expectedSize) {
        var receipt = cliReceiptParser.parse(receiptAsString);

        Assertions.assertEquals(expectedSize, receipt.getOrdersCount());
    }


    void configStorage() {
        Mockito.lenient().when(discountCardService.findCardByCode(1234)).thenReturn(
                Optional.of(new StandardDiscountCard(1, 20, 1234))
        );

        Mockito.lenient().when(productService.findProductById(1)).thenReturn(
                Optional.of(new Product(1, 2D))
        );

        Mockito.lenient().when(productService.findProductById(2)).thenReturn(
                Optional.of(new Product(2, 5D))
        );
    }


}
