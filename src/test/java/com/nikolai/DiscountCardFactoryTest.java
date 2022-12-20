package com.nikolai;

import com.nikolai.decorator.StandardDiscountCard;
import com.nikolai.decorator.ZeroDiscountCard;
import com.nikolai.factory.DiscountCardFactory;
import com.nikolai.factory.StandardDiscountCardFactory;
import com.nikolai.factory.ZeroDiscountCardFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DiscountCardFactoryTest {

    DiscountCardFactory factory;


    @Test
    public void whenStandardDiscountCardFactory_thenReturnEmptyStandardCard() {
        factory = new StandardDiscountCardFactory();
        var card = factory.produce();

        Assertions.assertTrue(card instanceof StandardDiscountCard);
        Assertions.assertNull(card.getDiscount());
        Assertions.assertNull(card.getId());
        Assertions.assertNull(card.getCode());
    }

    @Test
    public void whenZeroDiscountCardFactory_thenReturnEmptyZeroCardWithDiscountZero(){
        factory = new ZeroDiscountCardFactory();
        var card = factory.produce();

        Assertions.assertTrue(card instanceof ZeroDiscountCard);
        Assertions.assertEquals(0 , card.getDiscount());
        Assertions.assertNull(card.getId());
        Assertions.assertNull(card.getCode());
    }


}
