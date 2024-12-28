package com.example.demo.services;

import com.example.demo.Dto.CustomerDto;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.Deal;

import java.util.List;

public interface CustomerService {
    void addCustomer(Customer customer);
    void updateBalance(Customer customer, int totalCost);
    boolean existsById(int customerID);
    void deleteCustomer(int customerID);
    List<Deal> getCustomerDeals(int customerID);

    Customer getCustomerByUsername(String username);
    Customer getCustomerById(int id);

    void changeCustomerInfo(Customer customer);

}
