package com.nikolai.receiptTests;


import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.facade.ReceiptCreator;
import com.nikolai.factory.ZeroDiscountCardFactory;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.parser.CommandLineReceiptParser;
import com.nikolai.parser.FileReceiptParser;
import com.nikolai.parser.ReceiptParserProvider;
import com.nikolai.parser.WebReceiptParser;
import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReceiptCreatorTest {
    private ReceiptCreator receiptCreator;

    @Mock
    private ProductService productService;

    @Mock
    private DiscountCardService cardService;

    @Mock
    private ZeroDiscountCardFactory zeroDiscountCardFactory;

    @BeforeEach
    void init() {
        configureReceiptCreator();
        configureServices();
    }


    @AfterAll
    static void globalClean() throws IOException {
        deleteOutputFile();
    }


    @ParameterizedTest
    @MethodSource("com.nikolai.provider.ReceiptDataProvider#invalidReceipts")
    void whenInvalidReceipt_thenThrowUnsupportedPatternException(String receipt) {
        Assertions.assertThrows(UnsupportedPatternException.class, () -> receiptCreator.createReceipt(receipt));
    }

    @Test
    void whenValidReceipt_thenNotThrowUnsupportedPatternException() {
        String receipt = "1-1 1-2 2-5 card-1234";
        Assertions.assertDoesNotThrow(() -> receiptCreator.createReceipt(receipt));
    }

    @Test
    void whenCreateReceipt_thenVerifyServicesCalls() {
        String receipt = "1-1 1-2 2-5 card-1234";
        receiptCreator.createReceipt(receipt);

        Mockito.verify(productService, Mockito.times(2)).findProductById(1);
        Mockito.verify(productService, Mockito.times(1)).findProductById(2);
        Mockito.verify(cardService, Mockito.times(1)).findCardByCode(1234);

        Mockito.verifyNoMoreInteractions(productService);
        Mockito.verifyNoMoreInteractions(cardService);
        Mockito.verifyNoMoreInteractions(zeroDiscountCardFactory);
    }


    private void configureReceiptCreator() {
        var web = Mockito.mock(WebReceiptParser.class);
        var file = Mockito.mock(FileReceiptParser.class);
        var env = Mockito.mock(Environment.class);

        Mockito.lenient().doThrow(UnsupportedOperationException.class).when(web).parse(Mockito.anyString());
        Mockito.lenient().doThrow(UnsupportedOperationException.class).when(file).parse(Mockito.anyString());
        Mockito.lenient().doReturn("test1").when(env).getProperty(Mockito.anyString());

        receiptCreator = new ReceiptCreator(
                zeroDiscountCardFactory,
                web,
                file,
                new CommandLineReceiptParser(cardService, productService),
                new ReceiptParserProvider(),
                env
        );
    }

    private void configureServices() {
        Mockito.lenient().when(productService.findProductById(Mockito.any()))
                .thenAnswer(invocation -> {
                    int id = invocation.getArgument(0);
                    return switch (id) {
                        case 1 -> Optional.of(new Product(1, 2D));
                        case 2 -> Optional.of(new Product(2, 5D));
                        default -> Optional.empty();
                    };
                });
        Mockito.lenient().when(cardService.findCardByCode(Mockito.any()))
                .thenAnswer(
                        invocation -> (int) invocation.getArgument(0) != 1234 ?
                                Optional.empty() :
                                Optional.of(new StandardDiscountCard(1, 2))
                );
    }

    private static void deleteOutputFile() {
        try {
            Files.delete(Paths.get("test1"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
