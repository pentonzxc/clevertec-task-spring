package com.nikolai.receiptTests;


import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.facade.ReceiptCreator;
import com.nikolai.factory.ZeroDiscountCardFactory;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.card.ZeroDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class ReceiptCreatorTest {

    @Autowired
    private ReceiptCreator receiptCreator;

    @MockBean
    private ProductService productService;

    @MockBean
    private DiscountCardService cardService;

    @MockBean
    private ZeroDiscountCardFactory zeroDiscountCardFactory;

    @BeforeEach
    public void initServices() {
        Mockito.when(productService.findProductById(Mockito.any()))
                .thenAnswer(invocation -> {
                    int id = invocation.getArgument(0);
                    return switch (id) {
                        case 1 -> Optional.of(new Product(1, 2D));
                        case 2 -> Optional.of(new Product(2, 5D));
                        default -> Optional.empty();
                    };
                });


        Mockito.when(cardService.findCardByCode(Mockito.any()))
                .thenAnswer(invocation -> {
                    int code = invocation.getArgument(0);
                    return switch (code) {
                        case 2222 -> Optional.of(new StandardDiscountCard(1, 2));
                        default -> Optional.empty();
                    };
                });
    }


    @Test
    public void givenInvalidInput_thenReturnException() {
        String strWithUnknownId = "3-1 1-2 3-5 card-2222";
        String strWithUnknownCardCode = "1-1 1-2 2-5 card-3222";
        String strWithUnknownPattern = "1-2 -2 2-5";

        Assertions.assertThrows(UnsupportedPatternException.class, () -> receiptCreator.createReceipt(strWithUnknownId));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> receiptCreator.createReceipt(strWithUnknownCardCode));
        Assertions.assertThrows(UnsupportedPatternException.class, () -> receiptCreator.createReceipt(strWithUnknownPattern));
    }

    @Test
    public void givenInput_thenReturnReceipt() {
        var spyCreator = Mockito.spy(receiptCreator);
        String withCard = "1-1 1-2 2-5 card-2222";

        receiptCreator.createReceipt(withCard);

        Mockito.verify(productService, Mockito.times(2)).findProductById(1);
        Mockito.verify(productService, Mockito.times(1)).findProductById(2);
        Mockito.verify(cardService, Mockito.times(1)).findCardByCode(2222);

        Mockito.verifyNoMoreInteractions(productService);
        Mockito.verifyNoMoreInteractions(cardService);
        Mockito.verifyNoMoreInteractions(zeroDiscountCardFactory);


        String withOutCard = "1-1 1-2 2-5";

        Mockito.when(zeroDiscountCardFactory.produce()).thenReturn(new ZeroDiscountCard(1, 2));

        receiptCreator.createReceipt(withOutCard);

        Mockito.verify(productService, Mockito.times(4)).findProductById(1);
        Mockito.verify(productService, Mockito.times(2)).findProductById(2);
        Mockito.verify(zeroDiscountCardFactory, Mockito.times(1)).produce();

        Mockito.verifyNoMoreInteractions(productService);
        Mockito.verifyNoMoreInteractions(cardService);


//
//            Mockito.verify(productService.findProductById(Mockito.any()) , Mockito.times(2));
//        Mockito.verify(productService.findProductById(Mockito.any()) , Mockito.times(1));
//
//        Mockito.verify(cardService.findCardByCode(Mockito.any()) , Mockito.times(1));


    }

}
