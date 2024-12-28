package com.example.demo.Repositories;

import com.example.demo.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Optional<Category> findByName(String name);

}
