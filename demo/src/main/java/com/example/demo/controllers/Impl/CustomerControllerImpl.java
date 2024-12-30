package com.example.demo.controllers.Impl;


import com.example.demo.Dto.UpdateCustomerDto;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.Deal;
import com.example.demo.services.CustomerService;
import org.example.controllers.CustomerController;
import org.example.input.CustomerRegisterInputModel;
import org.example.input.CustomerUpdateInputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerControllerImpl implements CustomerController  {
    private final CustomerService customerService;

    @Autowired
    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }
    @Override
    @GetMapping("/getInfo/{customerID}")
    public String getCustomerInfo(@PathVariable("customerID") int customerID) {
        Customer customer = customerService.getCustomerById(customerID);
        if (customer == null) {
            return null;
        }
        return "OK";

    }
    @Override

    @PutMapping("/changeInfo/{customerID}")
    public String changeCustomerInfo(@PathVariable int customerID,
                                     @RequestBody CustomerUpdateInputModel customerDto) {
        Customer customer = customerService.getCustomerById(customerID);
        if (customer == null) {
            return null;
        }
        customer.setEmail(customerDto.getEmail());
        customer.setAge(customerDto.getAge());
        customer.setBonus_quantity(customerDto.getAge());
        customer.setUsername(customerDto.getUsername());
        customer.setGender(customerDto.getGender());
        customerService.changeCustomerInfo(customer);

        return "OK";
    }
    @Override

    @GetMapping("/getDeals/{customerID}")
    public String getCustomerDeals(@PathVariable int customerID) {
        Customer customer = customerService.getCustomerById(customerID);
        if (customer == null) {
            return null;
        }
        return "OK";
    }
    @Override

    @DeleteMapping("/deleteCustomer/{customerID}")
    public String deleteCustomer(@PathVariable int customerID) {
        if (!customerService.existsById(customerID)) {
            return "Customer not found";
        }
        customerService.deleteCustomer(customerID);
        return "OK";

    }
}
