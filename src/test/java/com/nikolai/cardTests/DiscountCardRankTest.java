package com.nikolai.cardTests;

import com.nikolai.decorator.BronzeDiscountCard;
import com.nikolai.decorator.GoldDiscountCard;
import com.nikolai.decorator.SilverDiscountCard;
import com.nikolai.model.card.DiscountCard;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.card.ZeroDiscountCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DiscountCardRankTest {

    static DiscountCard standartDiscountCard;

    static DiscountCard zeroDiscountCard;

    @BeforeAll
    public static void global() {
        standartDiscountCard = new StandardDiscountCard(1, 2);
        zeroDiscountCard = new ZeroDiscountCard(2, 3);
    }


    @Nested
    class BronzeDiscountCardTest {
        @Test
        public void whenBronzeStandardDiscountCard_thenReturnBronzeRankStandardType() {
            var bronzeGoldSilverStandard = new BronzeDiscountCard(
                    new GoldDiscountCard(new SilverDiscountCard(standartDiscountCard)));

            var mixedStandard = new BronzeDiscountCard(new SilverDiscountCard(bronzeGoldSilverStandard));

            Assertions.assertEquals("Bronze Standard Discount Card", mixedStandard.rank());

        }

        @Test
        public void whenBronzeZeroDiscountCard_thenReturnBronzeRankZeroType() {
            var bronzeGoldSilverZero = new BronzeDiscountCard(
                    new GoldDiscountCard(new SilverDiscountCard(zeroDiscountCard)));

            var mixedZero = new BronzeDiscountCard(new SilverDiscountCard(bronzeGoldSilverZero));

            Assertions.assertEquals("Bronze Zero Discount Card", mixedZero.rank());
        }


        @Test
        public void whenBronzeCardDiscountCard_thenSupportsUpgradeReturnTrue() {
            var bronzeStandard = new BronzeDiscountCard(standartDiscountCard);

            Assertions.assertTrue(bronzeStandard.supportsUpgrade());
        }

    }

    @Nested
    class SilverDiscountCardTest {

        @Test
        public void whenSilverStandardDiscountCard_thenReturnSilverRankStandardType() {
            var silverGoldBronzeStandard = new SilverDiscountCard(
                    new GoldDiscountCard(new BronzeDiscountCard(standartDiscountCard)));

            var mixedStandard = new SilverDiscountCard(new BronzeDiscountCard(silverGoldBronzeStandard));

            Assertions.assertEquals("Silver Standard Discount Card", mixedStandard.rank());
        }

        @Test
        public void whenSilverZeroDiscountCard_thenReturnSilverRankZeroType() {
            var silverGoldBronzeZero = new SilverDiscountCard(
                    new GoldDiscountCard(new BronzeDiscountCard(zeroDiscountCard)));

            var mixedZero = new SilverDiscountCard(new BronzeDiscountCard(silverGoldBronzeZero));

            Assertions.assertEquals("Silver Zero Discount Card", mixedZero.rank());
        }

        @Test
        public void whenSilverCardDiscountCard_thenSupportsUpgradeReturnTrue() {
            var silverStandard = new SilverDiscountCard(standartDiscountCard);

            Assertions.assertTrue(silverStandard.supportsUpgrade());
        }

    }


    @Nested
    class GoldDiscountCardTest {

        @Test
        public void whenGoldStandardDiscountCard_thenReturnGoldRankStandardType() {
            var goldSilverBronzeStandard = new GoldDiscountCard(
                    new SilverDiscountCard(new BronzeDiscountCard(standartDiscountCard)));

            var mixedStandard = new GoldDiscountCard(new BronzeDiscountCard(goldSilverBronzeStandard));

            Assertions.assertEquals("Gold Standard Discount Card", mixedStandard.rank());
        }

        @Test
        public void whenGoldZeroDiscountCard_thenReturnGoldRankZeroType() {
            var goldSilverBronzeZero = new GoldDiscountCard(
                    new SilverDiscountCard(new SilverDiscountCard(zeroDiscountCard)));

            var mixedZero = new GoldDiscountCard(new BronzeDiscountCard(goldSilverBronzeZero));

            Assertions.assertEquals("Gold Zero Discount Card", mixedZero.rank());
        }


        @Test
        public void whenGoldCardDiscountCard_thenSupportsUpgradeReturnFalse() {
            var goldStandard = new GoldDiscountCard(standartDiscountCard);

            Assertions.assertFalse(goldStandard.supportsUpgrade());
        }
    }


}
