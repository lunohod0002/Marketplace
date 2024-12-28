package com.example.demo.Repositories;

import com.example.demo.Entities.Product;
import com.example.demo.Entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {

    Optional<Seller> findByEmail(String email);

    Optional<Seller> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Seller cs SET cs.deleted= true  where cs.id=:sellerID")
    void deleteSeller(@Param("sellerID") int sellerID);


    @Query("select pr from Product pr join pr.seller s where s.email=:email")
    List<Product> getAllSellerProducts(@Param("email") String email);


}
