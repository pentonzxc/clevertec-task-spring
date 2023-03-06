package com.nikolai.provider;

import java.util.stream.Stream;

public class ReceiptDataProvider {

    private ReceiptDataProvider(){
    }

    public static String receipt() {
        return "2-3 1-5 2-1 card-1234";
    }

    public static String receiptWithoutCard() {
        return "2-3 1-5 2-1";
    }

    public static Stream<CharSequence> receipts() {
        return Stream.of(
                receipt(),
                receiptWithoutCard()
        );
    }

    public static Stream<CharSequence> invalidReceipts() {
        return Stream.of(
                "",
                "1- 2-5 card-1222",
                "1-0",
                "-2 2-3 card-1222",
                "card-1234",
                "card-123",
                "card-12344",
                "card-1234a",
                "card1234"
        );
    }


}
