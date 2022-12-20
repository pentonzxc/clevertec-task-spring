package com.nikolai.constants;

public class PatternConstants {
    public static String ORDER_PATTERN = ("[1-9]\\d*-[1-9]\\d*");

    public static String WEB_PRODUCT_PATTERN = ("itemId=([1-9]\\d*)");

    public static String WEB_PRODUCT_QUANTITY_PATTERN = ("quantity=([1-9]\\d*)");

    public static String WEB_DISCOUNT_CARD_PATTERN = ("card=(\\d{4})");
    public static String DISCOUNT_CARD_PATTERN = ("card-\\d{4}");
}
