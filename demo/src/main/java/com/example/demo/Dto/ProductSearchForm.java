package com.example.demo.Dto;

import jakarta.validation.constraints.Min;

public record ProductSearchForm(
String searchTerm,
String priceFilter,
String sellerFilter,
String season,

@Min(value = 0, message = "Страница должна быть больше 0")
Integer page,
@Min(value = 1, message = "Размер страницы должен быть больше 0")
Integer size,
String category
) {
}
