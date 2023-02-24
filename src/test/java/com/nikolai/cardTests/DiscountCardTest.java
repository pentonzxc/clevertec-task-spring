package com.nikolai.cardTests;

import com.nikolai.model.card.DiscountCard;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.card.ZeroDiscountCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DiscountCardTest {

    @Nested
    class ZeroDiscountCardTest {
        DiscountCard discountCard;

        @BeforeEach
        public void init() {
            discountCard = new ZeroDiscountCard(1, 2);
        }


        @Test
        public void whenCardWithoutCode_thenGetCodeReturnNull() {
            Assertions.assertNull(discountCard.getCode());
        }

        @Test
        public void whenSetCode_thenGetCodeReturnCode() {
            int expectedCode = 1234;
            discountCard.setCode(expectedCode);

            Assertions.assertEquals(expectedCode, discountCard.getCode());
        }


        @Test
        public void whenGetId_thenReturnId() {
            int expectedId = 2;
            discountCard = new ZeroDiscountCard(expectedId, 0);

            Assertions.assertEquals(expectedId, discountCard.getId());
        }

        @Test
        public void whenCardWithoutId_thenGetIdReturnNull() {
            discountCard = new ZeroDiscountCard();

            Assertions.assertNull(discountCard.getId());
        }

        @Test
        public void whenSetId_thenGetIdReturnId() {
            int expectedId = 2;
            discountCard.setId(expectedId);

            Assertions.assertEquals(expectedId, discountCard.getId());
        }

        @Test
        public void whenGetDiscount_thenReturnZero() {
            int expectedDiscount = 0;

            Assertions.assertEquals(expectedDiscount, discountCard.getDiscount());
        }

        @Test
        public void whenCardWithoutDiscount_thenGetDiscountZero() {
            int expectedDiscount = 0;

            discountCard = new ZeroDiscountCard();
            Assertions.assertEquals(expectedDiscount, discountCard.getDiscount());
        }

        @Test
        public void whenSetDiscount_thenGetDiscountReturnZero() {
            int expectedDiscount = 0;
            discountCard.setDiscount(2);

            Assertions.assertEquals(expectedDiscount, discountCard.getDiscount());
        }


        @Test
        public void whenGetType_thenReturnType() {
            Assertions.assertEquals("Zero Discount Card", discountCard.type());
        }

        @Test
        public void whenConstructorWithIdAndDiscountAndCode_thenReturnParamsSum() {
            int expectedId = 1;
            int expectedCode = 2;
            discountCard = new ZeroDiscountCard(expectedId, 0, expectedCode);

            Assertions.assertEquals(expectedId + expectedCode , discountCard.getCode() + discountCard.getId());
        }
    }


    @Nested
    class StandardDiscountCardTest {
        DiscountCard discountCard;

        @BeforeEach
        public void init() {
            discountCard = new StandardDiscountCard(1, 2);
        }

        @Test
        public void whenCardWithoutCode_thenGetCodeReturnNull() {
            Assertions.assertNull(discountCard.getCode());
        }

        @Test
        public void whenSetCode_thenGetCodeReturnCode() {
            int expectedCode = 1234;
            discountCard.setCode(expectedCode);

            Assertions.assertEquals(expectedCode, discountCard.getCode());
        }


        @Test
        public void whenGetId_thenReturnId() {
            int expectedId = 2;
            discountCard = new ZeroDiscountCard(expectedId, 0);

            Assertions.assertEquals(expectedId, discountCard.getId());
        }

        @Test
        public void whenCardWithoutId_thenGetIdReturnNull() {
            discountCard = new StandardDiscountCard();

            Assertions.assertNull(discountCard.getId());
        }

        @Test
        public void whenSetId_thenGetIdReturnId() {
            int expectedId = 2;
            discountCard.setId(expectedId);

            Assertions.assertEquals(expectedId, discountCard.getId());
        }

        @Test
        public void whenGetDiscount_thenReturnDiscount() {
            int expectedDiscount = 2;
            discountCard = new StandardDiscountCard(0, 2);

            Assertions.assertEquals(expectedDiscount, discountCard.getDiscount());
        }

        @Test
        public void whenCardWithoutDiscount_thenGetDiscountReturnNull() {
            discountCard = new StandardDiscountCard();
            Assertions.assertNull(discountCard.getDiscount());
        }

        @Test
        public void whenSetDiscount_thenGetDiscountReturnDiscount() {
            int expectedDiscount = 2;
            discountCard.setDiscount(expectedDiscount);

            Assertions.assertEquals(expectedDiscount, discountCard.getDiscount());
        }


        @Test
        public void whenGetType_thenReturnType() {
            Assertions.assertEquals("Standard Discount Card", discountCard.type());
        }
    }

}
