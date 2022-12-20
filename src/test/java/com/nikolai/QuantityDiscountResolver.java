package com.nikolai;

import com.nikolai.constants.CardRankConstants;
import com.nikolai.constants.ProductConstants;
import com.nikolai.decorator.BronzeDiscountCard;
import com.nikolai.decorator.DiscountCard;
import com.nikolai.decorator.StandardDiscountCard;
import org.junit.jupiter.api.Test;

public class QuantityDiscountResolver {
    private DiscountCard discountCard;


    @Test
    public void givenBronzeDiscountCard_thenReturnBronzeDiscount(){
        int expectedDiscount = CardRankConstants.BRONZE_QUANTITY_DISCOUNT + ProductConstants.WITH_QUANTITY_DISCOUNT;
        discountCard = new BronzeDiscountCard(new StandardDiscountCard());

//        var actualDiscount = resolve(discountCard);

    }
}
