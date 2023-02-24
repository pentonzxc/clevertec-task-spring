package com.nikolai.parserTests;

import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.parser.FileReceiptParser;
import com.nikolai.provider.ReceiptDataProvider;
import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.UUID;

public class FileReceiptParserTest {
    @InjectMocks
    FileReceiptParser fileReceiptParser;

    @Mock
    ProductService productService;

    @Mock
    DiscountCardService discountCardService;

    File file;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        configStorage();
        file = FilesUtil.createFile();
    }

    @AfterEach
    public void cleanUp() {
        FilesUtil.deleteFile(file);
    }


    @ParameterizedTest
    @MethodSource("com.nikolai.provider.ReceiptDataProvider#invalidReceipts")
    public void whenInvalidReceipt_thenThrowUnknownPatternException(String receipt) throws IOException {
        FilesUtil.writeStringInFile(Paths.get(file.getAbsolutePath()), receipt);

        Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(file.getAbsolutePath()));
    }


    @ParameterizedTest
    @MethodSource("com.nikolai.provider.ReceiptDataProvider#receipts")
    public void whenValidReceipt_thenNotThrowException(String receipt) {
        FilesUtil.writeStringInFile(Paths.get(file.getAbsolutePath()), receipt);

        Assertions.assertDoesNotThrow(() -> fileReceiptParser.parse(file.getAbsolutePath()));
    }

    @Test
    public void whenReceiptWithCard_thenReceiptGetCard() {
        var receiptAsString = ReceiptDataProvider.receipt();
        var discountCard = discountCardService.findCardByCode(1234).get();
        FilesUtil.writeStringInFile(Paths.get(file.getAbsolutePath()), receiptAsString);

        var receipt = fileReceiptParser.parse(file.getAbsolutePath());
        Assertions.assertSame(receipt.getDiscountCard(), discountCard);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 4",
            "1, 5",
    })
    public void whenReceipt_checkProductsQuantity(int productId, int productQuantity) {
        var receiptAsString = ReceiptDataProvider.receipt();
        FilesUtil.writeStringInFile(Paths.get(file.getAbsolutePath()), receiptAsString);

        var receipt = fileReceiptParser.parse(file.getAbsolutePath());
        Assertions.assertEquals(receipt.get(productId).getQuantity(), productQuantity);
    }


    @ParameterizedTest
    @CsvSource({
            "2-3 1-5 2-1, 2"
    })
    public void whenReceipt_checkOrdersCount(String receiptAsString, int expectedSize) {
        FilesUtil.writeStringInFile(Paths.get(file.getAbsolutePath()), receiptAsString);
        var receipt = fileReceiptParser.parse(file.getAbsolutePath());

        Assertions.assertEquals(expectedSize, receipt.getOrdersCount());
    }


    public void configStorage() {
        Mockito.when(discountCardService.findCardByCode(1234)).thenReturn(
                Optional.of(new StandardDiscountCard(1, 20, 1234))
        );
        Mockito.when(productService.findProductById(1)).thenReturn(
                Optional.of(new Product(1, 2D))
        );
        Mockito.when(productService.findProductById(2)).thenReturn(
                Optional.of(new Product(2, 5D))
        );
    }

    class FilesUtil {
        private FilesUtil() {
        }

        public static void writeStringInFile(Path path, String target) {
            try {
                Files.writeString(
                        path,
                        target,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static File createFile() {
            var file = new File(UUID.randomUUID() + ".txt");
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public static void deleteFile(File file) {
            file.deleteOnExit();
        }
    }
}
