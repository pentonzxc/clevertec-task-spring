package com.nikolai.utilTests;

import com.nikolai.constants.CardRankConstants;
import com.nikolai.constants.ProductConstants;
import com.nikolai.decorator.BronzeDiscountCard;
import com.nikolai.decorator.GoldDiscountCard;
import com.nikolai.decorator.SilverDiscountCard;
import com.nikolai.model.card.DiscountCard;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.product.ProductOrder;
import com.nikolai.util.ProductDiscountResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProductDiscountResolverTest {
    private DiscountCard discountCard;


    @Test
    void givenBronzeDiscountCard_thenReturnBronzeDiscount() {
        int expectedDiscount = CardRankConstants.BRONZE_QUANTITY_DISCOUNT + ProductConstants.WITH_QUANTITY_DISCOUNT;
        discountCard = new BronzeDiscountCard(new StandardDiscountCard(1, 0));
        ProductOrder productOrder = Mockito.mock(ProductOrder.class);
        Mockito.when(productOrder.getQuantity()).thenReturn(5);

        var actualDiscount = ProductDiscountResolver.resolve(productOrder, discountCard);

        Mockito.when(productOrder.getQuantity()).thenReturn(3);

        int expectedLowerDiscount = 0;

        var actualLowerDiscount = ProductDiscountResolver.resolve(productOrder, discountCard);


        Assertions.assertEquals(expectedDiscount, actualDiscount);
        Assertions.assertEquals(expectedLowerDiscount, actualLowerDiscount);
    }

    @Test
    void givenSilverDiscountCard_thenReturnBronzeDiscount() {
        int expectedDiscount = CardRankConstants.SILVER_QUANTITY_DISCOUNT + ProductConstants.WITH_QUANTITY_DISCOUNT;
        discountCard = new SilverDiscountCard(new StandardDiscountCard(1, 0));
        ProductOrder productOrder = Mockito.mock(ProductOrder.class);
        Mockito.when(productOrder.getQuantity()).thenReturn(5);

        var actualDiscount = ProductDiscountResolver.resolve(productOrder, discountCard);

        Mockito.when(productOrder.getQuantity()).thenReturn(3);

        int expectedLowerDiscount = 0;

        var actualLowerDiscount = ProductDiscountResolver.resolve(productOrder, discountCard);


        Assertions.assertEquals(expectedDiscount, actualDiscount);
        Assertions.assertEquals(expectedLowerDiscount, actualLowerDiscount);
    }

    @Test
    void givenGoldDiscountCard_thenReturnBronzeDiscount() {
        int expectedDiscount = CardRankConstants.GOLD_QUANTITY_DISCOUNT + ProductConstants.WITH_QUANTITY_DISCOUNT;
        discountCard = new GoldDiscountCard(new StandardDiscountCard(1, 0));
        ProductOrder productOrder = Mockito.mock(ProductOrder.class);
        Mockito.when(productOrder.getQuantity()).thenReturn(5);


        var actualDiscount = ProductDiscountResolver.resolve(productOrder, discountCard);

        Mockito.when(productOrder.getQuantity()).thenReturn(3);

        int expectedLowerDiscount = 0;

        var actualLowerDiscount = ProductDiscountResolver.resolve(productOrder, discountCard);


        Assertions.assertEquals(expectedDiscount, actualDiscount);
        Assertions.assertEquals(expectedLowerDiscount, actualLowerDiscount);
    }

    @Test
    void givenDiscountCard_thenReturnDiscount() {
        int expectedDiscount = ProductConstants.WITH_QUANTITY_DISCOUNT;
        discountCard = new StandardDiscountCard(1, 0);
        ProductOrder productOrder = Mockito.mock(ProductOrder.class);
        Mockito.when(productOrder.getQuantity()).thenReturn(5);

        var actualDiscount = ProductDiscountResolver.resolve(productOrder, discountCard);

        Mockito.when(productOrder.getQuantity()).thenReturn(3);

        int expectedLowerDiscount = 0;

        var actualLowerDiscount = ProductDiscountResolver.resolve(productOrder, discountCard);


        Assertions.assertEquals(expectedDiscount, actualDiscount);
        Assertions.assertEquals(expectedLowerDiscount, actualLowerDiscount);
    }


}
