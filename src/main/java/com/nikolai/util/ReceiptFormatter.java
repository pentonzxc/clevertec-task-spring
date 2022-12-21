package com.nikolai.util;

import com.nikolai.model.Receipt;
import com.nikolai.model.product.Product;
import com.nikolai.model.product.ProductOrder;
import com.nikolai.service.ReceiptService;

import java.util.Map;

public class ReceiptFormatter {

    private final ReceiptService receiptService;

    public ReceiptFormatter(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }


    public String format(Receipt receipt) {

        StringBuilder result = new StringBuilder();
        /*
         *  dots amount in View
         * */

        int dots_in_row = 107;
        int dots_in_side = 50;
        int dots_in_last = 57;

        /*
         *
         *  process logo
         *
         * */
        result.append(".".repeat(dots_in_side)).append("Receipt").append(".".repeat(dots_in_side)).append("\n");
        result.append("ID").append(".".repeat(dots_in_side - 2)).append("Quantity");
        result.append(".".repeat(dots_in_side - 4)).append("Sum\n");

        for (Map.Entry<Integer, ProductOrder> entry : receipt) {
            ProductOrder order = entry.getValue();

            Product product = order.getProduct();
            Integer quantity = order.getQuantity();

            int id_length = product.getId().toString().length();
            int quantity_length = quantity.toString().length();

            /*
             *
             * process line with id & quantity & pure sum
             *
             * */

            result.append(product.getId()).append(".".repeat(dots_in_side - id_length)).append(quantity);

            Double pureSum = product.getPrice() * quantity;
            var pureSumFormat = String.format("%.3f", pureSum);
            var priceFormat = String.format("%.3f", product.getPrice());

            int f_eq_length = priceFormat.length() + quantity_length + 2 + pureSumFormat.length();

            result.append(".".repeat(dots_in_last - f_eq_length - quantity_length));
            result.append(priceFormat);
            result.append("*").append(quantity).append("=").append(pureSumFormat).append("\n");

            Double discountPrice = receiptService.discountPrice(product);
            Double discountSum = discountPrice * quantity;

            var discountPriceFormat = String.format("%.3f", discountPrice);
            var discountSumFormat = String.format("%.3f", discountSum);

            /*
             *
             * process line with discount sum
             *
             * */

            int s_eq_length = discountPriceFormat.length() + quantity_length + 2 + discountSumFormat.length();

            result.append(".".repeat(dots_in_row - s_eq_length)).append(discountPriceFormat);
            result.append("*").append(quantity).append("=").append(discountSumFormat);
            result.append("\n");
        }

        var summaryPrice = receiptService.summaryPrice();
        var summaryDiscountedPrice = receiptService.summaryDiscountedPrice();

        var summaryPriceFormat = String.format("%.3f", summaryPrice);
        var summaryDiscountPriceFormat = String.format("%.3f", summaryDiscountedPrice);

        result.append(".".repeat(dots_in_row)).append("\n");
        result.append(".".repeat(dots_in_side)).append("Sum").append(".".repeat(dots_in_side + 4 - summaryPriceFormat.length()));
        result.append(summaryPriceFormat).append("\n");
        result.append(".".repeat(dots_in_side)).append("Discounted sum").append(".".repeat(dots_in_side - 7 - summaryDiscountPriceFormat.length()));
        result.append(String.format("%.3f", summaryDiscountedPrice)).append("\n");

        return result.toString();
    }


}



