package com.example.demo.Entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    private String title;
    private String description;
    private String photoURL;
    private int quantity;
    private int price;
    private boolean deleted;
    private List<DealProduct> dealProductList;
    private Seller seller;
    private List<Comment> comments;
    private Category category;

    public Product(String title, String description, String photoURL, int quantity, int price, Seller seller, Category category) {
        this.title = title;
        this.description = description;
        this.photoURL = photoURL;
        this.quantity = quantity;
        this.price = price;
        this.dealProductList=null;
        this.seller = seller;
        this.deleted = false;
        this.category = category;
    }

    protected Product() {
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "deleted", nullable = false)
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "price", nullable = false)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Column(name = "photo_url")
    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    public List<DealProduct> getDealProductList() {
        return dealProductList;
    }

    public void setDealProductList(List<DealProduct> dealProductList) {
        this.dealProductList = dealProductList;
    }
}
