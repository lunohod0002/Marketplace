package com.example.demo.services.Impl;

import com.example.demo.Entities.Seller;
import com.example.demo.Repositories.SellerRepository;
import com.example.demo.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Seller getSellerById(int sellerId) {
       Optional<Seller> seller= sellerRepository.findById(sellerId);
       return seller.orElse(null);
    }
    public void addSeller(Seller seller){
        sellerRepository.save(seller);
    }

    @Override
    public Seller getSellerByUsername(String username) {
        Optional<Seller> seller= sellerRepository.findByUsername(username);
        return seller.orElse(null);
    }

    @Override
    public boolean existsById(int sellerID) {
        return sellerRepository.existsById(sellerID);
    }

    @Override
    public void deleteSeller(int sellerID) {
        sellerRepository.deleteSeller(sellerID);
    }

/*
    public Seller getSeller(String username) {
        return sellerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
    }*/
}
