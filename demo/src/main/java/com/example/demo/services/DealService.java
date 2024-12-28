package com.example.demo.services;

import com.example.demo.Dto.DealDto;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.Deal;
import com.example.demo.Entities.DealProduct;

import java.util.List;

public interface DealService {
    void addDeal(List<DealProduct> products, Customer customer);

    List<DealDto> getCustomerDeals(int customer_id);
}
