package com.example.demo.services;

import com.example.demo.Dto.CategoryDto;
import com.example.demo.Entities.Category;

public interface CategoryService {
    void addCategory(CategoryDto categoryDto);

    Category findByName(String name);


}
