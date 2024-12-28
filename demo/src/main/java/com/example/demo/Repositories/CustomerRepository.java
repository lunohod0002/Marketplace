package com.example.demo.Repositories;

import com.example.demo.Entities.Customer;
import com.example.demo.Entities.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByEmail(String email);

    @Query("select cs from Customer cs  where cs.username=:username")
    Optional<Customer> findByUsername(@Param("username") String username);

    @Modifying
    @Transactional

    @Query("UPDATE Customer  cs SET cs.balance= cs.balance - :totalCost where cs.id=:customerID")
    void changeBalance(@Param("totalCost") int totalCost, int customerID);

    @Modifying
    @Transactional
    @Query("UPDATE Customer cs SET cs.deleted= true  where cs.id=:customerID")
    void deleteCustomer(@Param("customerID") int customerID);

    @Query("select cs from Customer cs where cs.age > :age")
    List<Customer> findAllByAgeAbove(@Param("age") int age);

    @Query("select cs.deals from Customer cs where cs.id = :customer_id")
    List<Deal> findCustomerDeals(@Param("customer_id") int customerID);
}
