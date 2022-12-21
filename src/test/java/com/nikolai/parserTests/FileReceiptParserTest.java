package com.nikolai.parserTests;

import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.parser.FileReceiptParser;
import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.UUID;

public class FileReceiptParserTest {
    @InjectMocks
    private FileReceiptParser fileReceiptParser;

    @Mock
    private ProductService productService;

    @Mock
    private DiscountCardService discountCardService;

    private File file;


    @BeforeEach
    public void init() {
        file = new File(UUID.randomUUID() + ".txt");
        try {
            MockitoAnnotations.openMocks(this);
            file.createNewFile();
        } catch (IOException ignored) {
        }
    }

    @AfterEach
    public void clenUpEach() {
        file.deleteOnExit();
    }


    @Test
    public void givenMismatchInputFile_thenReturnUnknownPatternException() {
        String emptyLine = "";
        String orderWithoutQuantity = "1- 2-5 card-1222";
        String orderWithZeroQuantity = "1-0";
        String orderWithoutId = "-2 2-3 card-1222";
        String invalidCardFormat1 = "card-123";
        String onlyCard = "card-1234";
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


        Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(onlyCard));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(emptyLine));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(orderWithoutId));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(orderWithZeroQuantity));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(orderWithoutQuantity));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(invalidCardFormat1));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(invalidCardFormat2));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(invalidCardFormat3));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(invalidCardFormat4));

        var path = Paths.get(file.getAbsolutePath());
        try {
            Files.writeString(path, emptyLine, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(file.getAbsolutePath()));

            Files.writeString(path, orderWithoutId, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(file.getAbsolutePath()));

            Files.writeString(path, invalidCardFormat1, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println(Files.readString(path));
            Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(file.getAbsolutePath()));

            Files.writeString(path, invalidCardFormat2,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(file.getAbsolutePath()));

            Files.writeString(path, orderWithZeroQuantity,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(file.getAbsolutePath()));

            Files.writeString(path, onlyCard,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(file.getAbsolutePath()));

            Files.writeString(path, invalidCardFormat3, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(file.getAbsolutePath()));

            Files.writeString(path, invalidCardFormat4, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(file.getAbsolutePath()));


        } catch (IOException ignored) {
        }
    }


    @Test
    public void givenCorrectInputFile_thenReturnReceipt() {
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

        var path = Paths.get(file.getAbsolutePath());


        Assertions.assertDoesNotThrow(() -> {
            Files.writeString(path, withoutCard,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            var receipt = fileReceiptParser.parse(file.getAbsolutePath());
            int expectedSize = 2;

            Assertions.assertEquals(expectedSize, receipt.getOrdersCount());
            Assertions.assertEquals(4, receipt.get(2).getQuantity());
            Assertions.assertEquals(5, receipt.get(1).getQuantity());
        });

        Assertions.assertDoesNotThrow(() -> {
            Files.writeString(path, withCard,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            var receipt = fileReceiptParser.parse(file.getAbsolutePath());
            int expectedSize = 2;

            Assertions.assertEquals(discountCard, receipt.getDiscountCard());
            Assertions.assertEquals(expectedSize, receipt.getOrdersCount());
            Assertions.assertEquals(4, receipt.get(2).getQuantity());
            Assertions.assertEquals(5, receipt.get(1).getQuantity());
        });


        Assertions.assertDoesNotThrow(() -> {
            Files.writeString(path, oneProduct,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            var receipt = fileReceiptParser.parse(file.getAbsolutePath());
            int expectedSize = 1;

            Assertions.assertEquals(5, receipt.get(1).getQuantity());
            Assertions.assertEquals(expectedSize, receipt.getOrdersCount());
        });
    }
}
