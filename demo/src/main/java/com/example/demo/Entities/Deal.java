package com.example.demo.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "deals")
public class Deal extends BaseEntity {
    private int totalCost;
    private LocalDate dateOfCreation;
    private Customer customer;

    public Deal(int totalCost, Customer customer) {
        this.totalCost = totalCost;
        this.dateOfCreation = LocalDate.now();
        this.customer = customer;
    }

    protected Deal() {
    }

    @Column(name = "totalCost")
    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    @Column(name = "date_of_creation")
    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = LocalDate.now();
    }


    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
