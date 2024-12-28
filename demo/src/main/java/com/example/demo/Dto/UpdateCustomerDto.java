package com.example.demo.Dto;

public class UpdateCustomerDto {
    private String username;
    private int bonus_quantity;
    private int age;
    private String email;
    private String password;
    private String gender;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBonus_quantity() {
        return bonus_quantity;
    }

    public void setBonus_quantity(int bonus_quantity) {
        this.bonus_quantity = bonus_quantity;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
