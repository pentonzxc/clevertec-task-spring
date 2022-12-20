package com.nikolai.model.card;


import com.nikolai.converter.CardCodeConverter;
import jakarta.persistence.*;


//@Entity
@MappedSuperclass
public abstract class DiscountCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer discount;
    @Convert(converter = CardCodeConverter.class)
    private Integer code;

    public DiscountCard() {
        this.id = null;
        this.discount = null;
    }

    public DiscountCard(Integer id, int discount) {
        this.id = id;
        this.discount = discount;
    }

    public DiscountCard(Integer id, int discount, Integer code) {
        this.id = id;
        this.discount = discount;
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public abstract String type();

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
