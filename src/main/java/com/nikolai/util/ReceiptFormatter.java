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

            int f_eq_length = product.getPrice().toString().length() + quantity_length + 2 + pureSum.toString().length();

            result.append(".".repeat(dots_in_last - f_eq_length - quantity_length));
            result.append(product.getPrice()).append("*").append(quantity).append("=").append(pureSum).append("\n");

            Double discountPrice = receiptService.discountPrice(product);
            Double discountSum = discountPrice * quantity;

            /*
             *
             * process line with discount sum
             *
             * */

            int s_eq_length = discountPrice.toString().length() + quantity_length + 2 + discountSum.toString().length();

            result.append(".".repeat(dots_in_row - s_eq_length)).append(discountPrice).append("*").append(quantity).append("=").append(discountSum);
            result.append("\n");
        }

        var summaryPrice = receiptService.summaryPrice();
        var summaryDiscountedPrice = receiptService.summaryDiscountedPrice();

        result.append(".".repeat(dots_in_row)).append("\n");
        result.append(".".repeat(dots_in_side)).append("Sum").append(".".repeat(dots_in_side + 4 - (summaryPrice + "").length()));
        result.append(summaryPrice).append("\n");
        result.append(".".repeat(dots_in_side)).append("Discounted sum").append(".".repeat(dots_in_side - 7 - (summaryDiscountedPrice + "").length()));
        result.append(summaryDiscountedPrice).append("\n");

        return result.toString();
    }


}



