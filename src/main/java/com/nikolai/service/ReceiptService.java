package com.nikolai.service;

import com.nikolai.model.Receipt;
import com.nikolai.model.card.DiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.model.product.ProductOrder;
import com.nikolai.util.ProductDiscountResolver;

import java.util.Map;


public class ReceiptService {
    private final Receipt receipt;

    private double cachedRealSummary = 0;

    private double cachedDiffSummary = 0;

    public ReceiptService(Receipt receipt) {
        this.receipt = receipt;
    }


    public double discountPrice(Integer productId) {
        ProductOrder order = receipt.get(productId);
        Double price = order.getProduct().getPrice();
        DiscountCard discountCard = receipt.getDiscountCard();

        var discount = ProductDiscountResolver.resolve(order, discountCard);

        return calculateDiscountPrice(price, discount);
    }


    public double discountPrice(Product product) {
        return discountPrice(product.getId());
    }


    private double calculateDiscountPrice(double oldPrice, int discount) {
        return (oldPrice * (100 - discount)) / 100;
    }

    private void calculateRealAndDiffSums() {
        this.cachedDiffSummary = 0D;
        this.cachedRealSummary = 0D;

        for (Map.Entry<Integer, ProductOrder> entry : receipt) {
            ProductOrder order = entry.getValue();
            int quantity = order.getQuantity();
            Double realPrice = order.getProduct().getPrice();

            double diffPrice = discountPrice(order.getProduct().getId());

            this.cachedRealSummary += realPrice * quantity;
            this.cachedDiffSummary += diffPrice * quantity;
        }
    }


    public double summaryPrice() {
        calculateRealAndDiffSums();
        return cachedRealSummary;
    }

    public double summaryDiscountedPrice() {
        calculateRealAndDiffSums();
        return cachedDiffSummary;
    }

    public Receipt getReceipt() {
        return receipt;
    }
}
