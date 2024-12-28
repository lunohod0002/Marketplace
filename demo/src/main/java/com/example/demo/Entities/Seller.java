package com.example.demo.Entities;

import jakarta.persistence.*;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class Seller extends User{

    private int rating;
    private int numberOfDeals;
    private List<Product> products;
    public Seller() {

    }

    public Seller( String username, String email, String password) {
        super(username,password, email);
        this.rating = 0;
        this.numberOfDeals = 0;
    }




    @Column(name="rating")
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    @Column(name="number_of_deals")
    public int getNumberOfDeals() {
        return numberOfDeals;
    }

    public void setNumberOfDeals(int numberOfDeals) {
        this.numberOfDeals = numberOfDeals;
    }

    @OneToMany(mappedBy = "seller")

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
