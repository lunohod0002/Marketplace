package com.example.demo.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "productdeals")
public class DealProduct extends BaseEntity{
    private int quantity;
    private Product product;
    private Deal deal;

    public DealProduct(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    protected DealProduct() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne
    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }
}
