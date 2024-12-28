package com.example.demo.controllers.Impl;


import com.example.demo.Dto.UpdateCustomerDto;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.Deal;
import com.example.demo.services.CustomerService;
import org.example.input.CustomerRegisterInputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerControllerImpl {
    private final CustomerService customerService;

    @Autowired
    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getInfo/{customerID}")
    public Customer getCustomerInfo(@PathVariable("customerID") int customerID) {
        Customer customer = customerService.getCustomerById(customerID);
        if (customer == null) {
            return null;
        }
        return customer;

    }

    @PutMapping("/changeInfo/{customerID}")
    public String changeCustomerInfo(@PathVariable int customerID,
                                     @RequestBody UpdateCustomerDto customerDto) {
        Customer customer = customerService.getCustomerById(customerID);
        if (customer == null) {
            return null;
        }
        customer.setEmail(customerDto.getEmail());
        customer.setAge(customerDto.getAge());
        customer.setBonus_quantity(customerDto.getBonus_quantity());
        customer.setUsername(customerDto.getUsername());
        customer.setGender(customerDto.getGender());
        customerService.changeCustomerInfo(customer);

        return "OK";
    }

    @GetMapping("/getDeals/{customerID}")
    public List<Deal> getCustomerDeals(@PathVariable int customerID) {
        Customer customer = customerService.getCustomerById(customerID);
        if (customer == null) {
            return null;
        }
        return customerService.getCustomerDeals(customerID);
    }
    @DeleteMapping("/deleteCustomer/{customerID}")
    public String deleteCustomer(@PathVariable int customerID) {
        if (!customerService.existsById(customerID)) {
            return "Customer not found";
        }
        customerService.deleteCustomer(customerID);
        return "OK";

    }
}
