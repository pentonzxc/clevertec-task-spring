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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class FileReceiptParserTest {
    @InjectMocks
    private FileReceiptParser fileReceiptParser;

    @Mock
    private ProductService productService;

    @Mock
    private DiscountCardService discountCardService;

    private File file;


    @BeforeEach
    void init() {
        configStorage();
        file = FilesUtil.createFile();
    }

    @AfterEach
    void cleanUp() {
        FilesUtil.deleteFile(file);
    }


    @ParameterizedTest
    @MethodSource("com.nikolai.provider.ReceiptDataProvider#invalidReceipts")
    void whenInvalidReceipt_thenThrowUnknownPatternException(String receipt) throws IOException {
        FilesUtil.writeStringInFile(Paths.get(file.getAbsolutePath()), receipt);

        Assertions.assertThrows(UnsupportedPatternException.class, () -> fileReceiptParser.parse(file.getAbsolutePath()));
    }


    @ParameterizedTest
    @MethodSource("com.nikolai.provider.ReceiptDataProvider#receipts")
    void whenValidReceipt_thenNotThrowException(String receipt) {
        FilesUtil.writeStringInFile(Paths.get(file.getAbsolutePath()), receipt);

        Assertions.assertDoesNotThrow(() -> fileReceiptParser.parse(file.getAbsolutePath()));
    }

    @Test
    void whenReceiptWithCard_thenReceiptGetCard() {
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
    void whenReceipt_checkProductsQuantity(int productId, int productQuantity) {
        var receiptAsString = ReceiptDataProvider.receipt();
        FilesUtil.writeStringInFile(Paths.get(file.getAbsolutePath()), receiptAsString);

        var receipt = fileReceiptParser.parse(file.getAbsolutePath());
        Assertions.assertEquals(receipt.get(productId).getQuantity(), productQuantity);
    }


    @ParameterizedTest
    @CsvSource({
            "2-3 1-5 2-1, 2"
    })
    void whenReceipt_checkOrdersCount(String receiptAsString, int expectedSize) {
        FilesUtil.writeStringInFile(Paths.get(file.getAbsolutePath()), receiptAsString);
        var receipt = fileReceiptParser.parse(file.getAbsolutePath());

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

    class FilesUtil {
        private FilesUtil() {
        }

        static void writeStringInFile(Path path, String target) {
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

        static File createFile() {
            var file = new File(UUID.randomUUID() + ".txt");
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        static void deleteFile(File file) {
            file.deleteOnExit();
        }
    }
}
