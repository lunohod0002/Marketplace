package com.example.demo.Entities;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Entity
public class Customer extends User {
    private String username;
    private int bonus_quantity;
    private int age;
    private String gender;
    public int balance;
    private List<Deal> deals;
    private List<Comment> comments;

    public Customer() {
    }

    public Customer(String username, String email, String password) {
        super(username, password, email);
        Random random = new Random();

        this.balance = random.nextInt(1, 10000);
        this.bonus_quantity = 0;
    }


    @Column(name = "bonus_quantity")
    public int getBonus_quantity() {
        return bonus_quantity;
    }

    public void setBonus_quantity(int bonus_quantity) {
        this.bonus_quantity = bonus_quantity;
    }

    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @OneToMany(mappedBy = "customer")
    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    @OneToMany(mappedBy = "customer")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Column(name = "balance")

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }


}
