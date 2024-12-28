package com.example.demo.Dto;

import com.example.demo.Entities.DealProduct;

import java.time.LocalDate;
import java.util.List;

public class DealDto {
    List<DealProductDto> dealProducts;
    int totalCost;

    public List<DealProductDto> getDealProducts() {
        return dealProducts;
    }

    public void setDealProducts(List<DealProductDto> dealProducts) {
        this.dealProducts = dealProducts;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}
