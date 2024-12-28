package com.example.demo.services.Impl;

import com.example.demo.Dto.CategoryDto;
import com.example.demo.Entities.Category;
import com.example.demo.Repositories.CategoryRepository;
import com.example.demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addCategory(CategoryDto categoryDto) {
    }

    @Override
    public Category findByName(String name) {
        Optional<Category> category=categoryRepository.findByName(name);
        return category.orElse(null);
    }
}
