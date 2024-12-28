package com.example.demo.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="comments")
public class Comment extends BaseEntity{
    private String text;
    private String title;
    private LocalDate dateAdded;
    private int mark;
    private Product product;
    private Customer customer;

    public Comment() {
    }

    public Comment(String text, String title, int mark, Product product, Customer customer) {
        this.text = text;
        this.title = title;
        this.dateAdded = LocalDate.now();
        this.mark = mark;
        this.product = product;
        this.customer = customer;
    }
    @Column(name = "text", columnDefinition = "TEXT")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    @Column(name = "title",nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Column(name = "dateAdded",nullable = false, columnDefinition = "TEXT")

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateOfLeaving) {
        this.dateAdded = dateOfLeaving;
    }
    @Column(name = "mark",nullable = false)

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
