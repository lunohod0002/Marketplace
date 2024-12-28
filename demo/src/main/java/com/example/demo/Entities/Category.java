package com.example.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    private String name;

    private String season;
    private String description;

    public Category(String name, String season, String description) {
        this.name = name;
        this.season = season;
        this.description = description;
    }

    protected Category() {
    }


    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name = "season")
    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
    /*    @Column(name = "parent_category_id")
    public int getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(int parent_category_id) {
        this.parent_category_id = parent_category_id;
    }*/
}
