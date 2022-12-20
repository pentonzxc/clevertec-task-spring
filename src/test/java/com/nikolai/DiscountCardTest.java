package com.nikolai;

import com.nikolai.decorator.DiscountCard;
import com.nikolai.decorator.StandardDiscountCard;
import com.nikolai.decorator.ZeroDiscountCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DiscountCardTest {
    private static DiscountCard standartDiscountCard;

    private static DiscountCard zeroDiscountCard;

    @BeforeAll
    public static void init() {
        standartDiscountCard = new StandardDiscountCard(1, 2);
        zeroDiscountCard = new ZeroDiscountCard(2, 3);
    }


    @Test
    public void whenCreateCardWithId_returnId() {
        Assertions.assertEquals(1, standartDiscountCard.getId());
        Assertions.assertEquals(2, zeroDiscountCard.getId());
    }


    @Test
    public void whenCreateCardWithDiscount_returnDiscount() {
        Assertions.assertEquals(2, standartDiscountCard.getDiscount());
    }


    @Test
    public void whenZeroDiscountCard_returnDiscountEqualsZero() {
        ZeroDiscountCard negativeCard = new ZeroDiscountCard(2, -5);

        ZeroDiscountCard emptyCard = new ZeroDiscountCard();

        Assertions.assertEquals(0, emptyCard.getDiscount());
        Assertions.assertEquals(0, negativeCard.getDiscount());
        Assertions.assertEquals(0, zeroDiscountCard.getDiscount());
    }

    @Test
    public void whenZeroDiscountCardNewDiscount_returnDiscountEqualsZero() {
        ZeroDiscountCard zeroCard = new ZeroDiscountCard(2, -5);
        zeroCard.setDiscount(5);
        Assertions.assertEquals(0, zeroCard.getDiscount());
    }

    @Test
    public void whenZeroDiscountCard_returnTypeEquals() {
        Assertions.assertEquals("Zero Discount Card", zeroDiscountCard.type());
    }


    @Test
    public void whenStandardDiscountCard_returnTypeEquals() {
        Assertions.assertEquals("Standard Discount Card", standartDiscountCard.type());
    }

    @Test
    public void whenStandardEmptyDiscountCard_thenReturnDiscount() {
        StandardDiscountCard card = new StandardDiscountCard();
        Assertions.assertNull(card.getDiscount());
    }


    @Test
    public void whenZeroEmptyDiscountCard_thenReturnId() {
        ZeroDiscountCard card = new ZeroDiscountCard();
        Assertions.assertNull(card.getId());
    }

    @Test
    public void whenStandardEmptyDiscountCard_thenReturnId() {
        StandardDiscountCard card = new StandardDiscountCard();
        Assertions.assertNull(card.getId());
    }

}
