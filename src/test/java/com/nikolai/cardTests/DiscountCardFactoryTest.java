package com.nikolai.cardTests;

import com.nikolai.factory.DiscountCardFactory;
import com.nikolai.factory.StandardDiscountCardFactory;
import com.nikolai.factory.ZeroDiscountCardFactory;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.card.ZeroDiscountCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DiscountCardFactoryTest {

    private DiscountCardFactory factory;


    @Test
    void whenStandardDiscountCardFactory_thenReturnStandardCard() {
        factory = new StandardDiscountCardFactory();
        var card = factory.produce();

        Assertions.assertTrue(card instanceof StandardDiscountCard);
    }

    @Test
    void whenZeroDiscountCardFactory_thenReturnEmptyZeroCardWithDiscountZero() {
        factory = new ZeroDiscountCardFactory();
        var card = factory.produce();

        Assertions.assertTrue(card instanceof ZeroDiscountCard);
    }


}
