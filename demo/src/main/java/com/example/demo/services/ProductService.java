package com.example.demo.services;

import com.example.demo.Dto.DealProductDto;
import com.example.demo.Dto.ProductDto;
import com.example.demo.Dto.ProductInfoDto;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    void addProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(int productID);

    boolean existsById(int productID);

    List<ProductDto> getSellerProducts(int sellerId);

    void changeProductQuantity(int productID, int quantity);

    Product getProductByName(String productTitle);

    Product getProductById(int productID);

    ProductDto getProductDtoById(int productID);

    Page<ProductInfoDto> getAllProducts(String searchTerm,String category,String priceFilter,String sellerFilter, String dateFilter,int page, int size);

    void buyProduct(Product product, int quantity, Customer customer);

    void buyProducts(List<DealProductDto> products, Customer customer);

    List<ProductDto> getAllProductsByCategoryWithThreeLastComments(String categoryName);

    List<ProductDto> getAllMostSoldProducts();


    List<ProductDto> getAllMostSoldProductsInThisSeason(String seasonName);

    Page<ProductInfoDto> getAllProductsByCategory(String searchTerm, int page, int size, String categoryName);
}



