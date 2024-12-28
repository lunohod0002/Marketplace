package com.example.demo.Repositories;

import com.example.demo.Dto.DealDto;
import com.example.demo.Entities.Deal;
import com.example.demo.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Integer>  {
    Deal findByTotalCost(int totalCost);

    @Query("select cs.deals from Customer cs where cs.id=:customer_id")
    List<Deal> findAllCustomerDeals(@Param("customer_id") int customer_id);
}
