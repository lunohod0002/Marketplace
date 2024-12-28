package com.example.demo.services;

import com.example.demo.Entities.Seller;

public interface SellerService {
    Seller getSellerById(int sellerID);
    boolean existsById(int sellerID);
    void deleteSeller(int sellerID);
    void addSeller(Seller seller);
    Seller getSellerByUsername(String username);

}
