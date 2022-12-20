package com.nikolai.model;

import com.nikolai.model.card.DiscountCard;
import com.nikolai.model.product.ProductOrder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Receipt implements Iterable<Map.Entry<Integer, ProductOrder>> {
    private final Map<Integer, ProductOrder> orders = new HashMap<>();
    private DiscountCard discountCard;

    public Receipt() {
        this.discountCard = null;
    }

    public void add(ProductOrder order) {
        orders.merge(order.getProduct().getId(), order,
                (newOrder, oldOrder) -> new ProductOrder(newOrder.getProduct(), newOrder.getQuantity() + oldOrder.getQuantity()));
    }

    public ProductOrder get(Integer integer) {
        return orders.get(integer);
    }

    public int getOrdersCount() {
        return orders.size();
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public void setDiscountCard(DiscountCard card) {
        this.discountCard = card;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "orders=" + orders +
                ", discountCard=" + discountCard +
                '}';
    }


    @Override
    public Iterator<Map.Entry<Integer, ProductOrder>> iterator() {
        return orders.entrySet().iterator();
    }
}
