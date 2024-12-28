package com.example.demo.Entities;

import jakarta.persistence.*;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")

public class User extends BaseEntity implements Serializable {

    private String email;
    private String username;
    private String password;
    private boolean deleted;
    private LocalDate dateOfRegistration;
    private List<Role> roles;

    public User() {
        this.roles = new ArrayList<>();
        this.deleted=false;
        this.dateOfRegistration = LocalDate.now();

    }
    public User(String username, String password,String email) {
        this();
        this.email = email;
        this.password = password;
        this.username=username;
    }




    @Column(name = "email", unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Column(name = "deleted")
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    @Column(name = "password", nullable = false)

    public String getPassword() {
        return password;
    }
    @Column(nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @ManyToMany(fetch = FetchType.EAGER)
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Column(name = "registration_date")

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }






}
