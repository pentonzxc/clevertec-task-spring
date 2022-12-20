package com.nikolai;

import com.nikolai.decorator.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiscountCardRankTest {

    private DiscountCard standartDiscountCard;

    private DiscountCard zeroDiscountCard;

    @BeforeEach
    public void init() {
        standartDiscountCard = new StandardDiscountCard(1, 2);
        zeroDiscountCard = new ZeroDiscountCard(2, 3);
    }

    @Test
    public void whenBronzeStandardDiscountCard_thenReturnRankBronzeWithType() {
        var bronzeStandard = new BronzeDiscountCard(standartDiscountCard);
        var bronzeSilverStandard = new BronzeDiscountCard(new SilverDiscountCard(standartDiscountCard));
        var bronzeGoldSilverStandard = new BronzeDiscountCard(
                new GoldDiscountCard(new SilverDiscountCard(standartDiscountCard)));

        var mixedStandard = new BronzeDiscountCard(new SilverDiscountCard(bronzeGoldSilverStandard));

        Assertions.assertEquals("Bronze Standard Discount Card", bronzeStandard.rank());
        Assertions.assertEquals("Bronze Standard Discount Card", bronzeSilverStandard.rank());
        Assertions.assertEquals("Bronze Standard Discount Card", bronzeGoldSilverStandard.rank());
        Assertions.assertEquals("Bronze Standard Discount Card", mixedStandard.rank());

    }


    @Test
    public void whenBronzeZeroDiscountCard_thenReturnRankWithType() {
        var bronzeZero = new BronzeDiscountCard(zeroDiscountCard);
        var bronzeSilverZero = new BronzeDiscountCard(new SilverDiscountCard(zeroDiscountCard));
        var bronzeGoldSilverZero = new BronzeDiscountCard(
                new GoldDiscountCard(new SilverDiscountCard(zeroDiscountCard)));

        var mixedZero = new BronzeDiscountCard(new SilverDiscountCard(bronzeGoldSilverZero));

        Assertions.assertEquals("Bronze Zero Discount Card", bronzeZero.rank());
        Assertions.assertEquals("Bronze Zero Discount Card", bronzeSilverZero.rank());
        Assertions.assertEquals("Bronze Zero Discount Card", bronzeGoldSilverZero.rank());
        Assertions.assertEquals("Bronze Zero Discount Card", mixedZero.rank());
    }


    @Test
    public void whenSilverZeroDiscountCard_thenReturnRankWithType() {
        var silverZero = new SilverDiscountCard(zeroDiscountCard);
        var silverBronzeZero = new SilverDiscountCard(new BronzeDiscountCard(zeroDiscountCard));
        var silverGoldBronzeZero = new SilverDiscountCard(
                new GoldDiscountCard(new BronzeDiscountCard(zeroDiscountCard)));

        var mixedZero = new SilverDiscountCard(new BronzeDiscountCard(silverGoldBronzeZero));

        Assertions.assertEquals("Silver Zero Discount Card", silverZero.rank());
        Assertions.assertEquals("Silver Zero Discount Card", silverBronzeZero.rank());
        Assertions.assertEquals("Silver Zero Discount Card", silverGoldBronzeZero.rank());
        Assertions.assertEquals("Silver Zero Discount Card", mixedZero.rank());
    }

    @Test
    public void whenSilverStandardDiscountCard_thenReturnRankWithType() {
        var silverStandard = new SilverDiscountCard(standartDiscountCard);
        var silverBronzeStandard = new SilverDiscountCard(new BronzeDiscountCard(standartDiscountCard));
        var silverGoldBronzeStandard = new SilverDiscountCard(
                new GoldDiscountCard(new BronzeDiscountCard(standartDiscountCard)));

        var mixedStandard = new SilverDiscountCard(new BronzeDiscountCard(silverGoldBronzeStandard));

        Assertions.assertEquals("Silver Standard Discount Card", silverStandard.rank());
        Assertions.assertEquals("Silver Standard Discount Card", silverBronzeStandard.rank());
        Assertions.assertEquals("Silver Standard Discount Card", silverGoldBronzeStandard.rank());
        Assertions.assertEquals("Silver Standard Discount Card", mixedStandard.rank());
    }

    @Test
    public void whenGoldStandardDiscountCard_thenReturnRankWithType() {
        var goldStandard = new GoldDiscountCard(standartDiscountCard);
        var goldBronzeStandard = new GoldDiscountCard(new BronzeDiscountCard(standartDiscountCard));
        var goldSilverBronzeStandard = new GoldDiscountCard(
                new SilverDiscountCard(new BronzeDiscountCard(standartDiscountCard)));

        var mixedStandard = new GoldDiscountCard(new BronzeDiscountCard(goldSilverBronzeStandard));

        Assertions.assertEquals("Gold Standard Discount Card", goldStandard.rank());
        Assertions.assertEquals("Gold Standard Discount Card", goldBronzeStandard.rank());
        Assertions.assertEquals("Gold Standard Discount Card", goldSilverBronzeStandard.rank());
        Assertions.assertEquals("Gold Standard Discount Card", mixedStandard.rank());
    }

    @Test
    public void whenGoldZeroDiscountCard_thenReturnRankWithType() {
        var goldZero = new GoldDiscountCard(zeroDiscountCard);
        var goldBronzeZero = new GoldDiscountCard(new BronzeDiscountCard(zeroDiscountCard));
        var goldSilverBronzeZero = new GoldDiscountCard(
                new SilverDiscountCard(new SilverDiscountCard(zeroDiscountCard)));

        var mixedZero = new GoldDiscountCard(new BronzeDiscountCard(goldSilverBronzeZero));

        Assertions.assertEquals("Gold Zero Discount Card", goldZero.rank());
        Assertions.assertEquals("Gold Zero Discount Card", goldBronzeZero.rank());
        Assertions.assertEquals("Gold Zero Discount Card", goldSilverBronzeZero.rank());
        Assertions.assertEquals("Gold Zero Discount Card", mixedZero.rank());
    }


    @Test
    public void whenGoldCardDiscountCard_thenReturnSupportsUpgrade() {
        var goldStandard = new GoldDiscountCard(standartDiscountCard);
        var zeroGold = new GoldDiscountCard(zeroDiscountCard);

        var mixedGold = new GoldDiscountCard(new SilverDiscountCard(goldStandard));

        Assertions.assertFalse(goldStandard.supportsUpgrade());
        Assertions.assertFalse(zeroGold.supportsUpgrade());
        Assertions.assertFalse(mixedGold.supportsUpgrade());
    }

    @Test
    public void whenSilverCardDiscountCard_thenReturnSupportsUpgrade() {
        var silverGold = new SilverDiscountCard(standartDiscountCard);
        var silverZero = new SilverDiscountCard(zeroDiscountCard);

        var mixedSilver = new SilverDiscountCard(new GoldDiscountCard(silverGold));

        Assertions.assertTrue(silverGold.supportsUpgrade());
        Assertions.assertTrue(silverZero.supportsUpgrade());
        Assertions.assertTrue(mixedSilver.supportsUpgrade());
    }


    @Test
    public void whenBronzeCardDiscountCard_thenReturnSupportsUpgrade() {
        var bronzeStandard = new BronzeDiscountCard(standartDiscountCard);
        var bronzeZero = new BronzeDiscountCard(zeroDiscountCard);

        var mixedSilver = new BronzeDiscountCard(new GoldDiscountCard(bronzeStandard));

        Assertions.assertTrue(bronzeStandard.supportsUpgrade());
        Assertions.assertTrue(bronzeZero.supportsUpgrade());
        Assertions.assertTrue(mixedSilver.supportsUpgrade());
    }


    @Test
    public void whenDiscountCardHasRank_thenReturnDiscount() {
        var goldStandard = new GoldDiscountCard(standartDiscountCard);
        var goldZero = new GoldDiscountCard(zeroDiscountCard);

        var silverStandard = new SilverDiscountCard(standartDiscountCard);
        var silverZero = new SilverDiscountCard(zeroDiscountCard);

        var bronzeStandard = new BronzeDiscountCard(standartDiscountCard);
        var bronzeZero = new BronzeDiscountCard(zeroDiscountCard);


        var mixed_1 = new BronzeDiscountCard(goldStandard);
        var mixed_2 = new SilverDiscountCard(mixed_1);

        Assertions.assertEquals(standartDiscountCard.getDiscount(), goldStandard.getDiscount());
        Assertions.assertEquals(zeroDiscountCard.getDiscount(), goldZero.getDiscount());

        Assertions.assertEquals(zeroDiscountCard.getDiscount(), silverZero.getDiscount());
        Assertions.assertEquals(standartDiscountCard.getDiscount(), silverStandard.getDiscount());

        Assertions.assertEquals(zeroDiscountCard.getDiscount(), bronzeZero.getDiscount());
        Assertions.assertEquals(standartDiscountCard.getDiscount(), bronzeStandard.getDiscount());

        Assertions.assertEquals(standartDiscountCard.getDiscount(), mixed_1.getDiscount());
        Assertions.assertEquals(standartDiscountCard.getDiscount(), mixed_2.getDiscount());
    }





}
