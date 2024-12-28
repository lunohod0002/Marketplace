package com.example.demo.services.Impl;

import com.example.demo.Dto.DealDto;
import com.example.demo.Dto.DealProductDto;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.Deal;
import com.example.demo.Entities.DealProduct;
import com.example.demo.Entities.Product;
import com.example.demo.Repositories.CustomerRepository;
import com.example.demo.Repositories.DealProductRepository;
import com.example.demo.Repositories.DealRepository;
import com.example.demo.Repositories.ProductRepository;
import com.example.demo.services.DealService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class DealServiceImpl implements DealService {
    private final DealRepository dealRepository;
    private final ProductRepository productRepository;
    private final DealProductRepository dealProductRepository;

    private  final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public DealServiceImpl(DealRepository dealRepository, ProductRepository productRepository, DealProductRepository dealProductRepository, CustomerRepository customerRepository) {
        this.dealRepository = dealRepository;
        this.productRepository = productRepository;
        this.dealProductRepository = dealProductRepository;
    }
    @Override
    public void addDeal(List<DealProduct> dealProducts, Customer customer) {
        Deal deal=new Deal(0,customer);
        int totalCost=0;
        for (DealProduct dealProduct:dealProducts){
            totalCost+=dealProduct.getProduct().getPrice();
            dealProduct.setDeal(deal);
            dealProductRepository.save(dealProduct);
        }
        deal.setTotalCost(totalCost);
        dealRepository.save(deal);
    }



    @Override
    public List<DealDto> getCustomerDeals(int customer_id) {
        return null;
    }
}
