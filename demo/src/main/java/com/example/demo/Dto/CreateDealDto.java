package com.example.demo.Dto;

import java.util.List;

public class CreateDealDto {
    private List<DealProductDto> products;

    public CreateDealDto() {
    }

    public List<DealProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<DealProductDto> products) {
        this.products = products;
    }
}
