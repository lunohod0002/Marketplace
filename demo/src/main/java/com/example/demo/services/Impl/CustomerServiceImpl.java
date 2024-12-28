package com.example.demo.services.Impl;

import com.example.demo.Dto.CustomerDto;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.Deal;
import com.example.demo.Repositories.CustomerRepository;
import com.example.demo.Repositories.DealRepository;
import com.example.demo.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void updateBalance(Customer customer, int totalCost) {
        customerRepository.changeBalance(totalCost, customer.getId());
    }

    @Override
    public boolean existsById(int customerID) {
        return customerRepository.existsById(customerID);
    }

    @Override
    public void deleteCustomer(int customerID) {
        customerRepository.deleteCustomer(customerID);
    }

    @Override
    public List<Deal> getCustomerDeals(int customerID) {
        List<Deal> deals = customerRepository.findCustomerDeals(customerID);
        return deals;
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isPresent()) {
            return customer.orElse(null);
        }
        return null;
    }

    @Override
    public Customer getCustomerById(int customerID) {
        Optional<Customer> customer = customerRepository.findById(customerID);
        if (customer.isPresent()) {
            return customer.orElse(null);
        }
        return null;
    }

    @Override
    public void changeCustomerInfo(Customer customer) {

        customerRepository.save(customer);

    }
/*    public Customer getCustomer(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }*/
}
