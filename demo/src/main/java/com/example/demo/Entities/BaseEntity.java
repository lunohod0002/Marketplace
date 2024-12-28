package com.example.demo.Entities;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    private int id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }
}
