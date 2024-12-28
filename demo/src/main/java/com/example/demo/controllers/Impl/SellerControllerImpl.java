package com.example.demo.controllers.Impl;


import com.example.demo.Dto.ProductDto;
import com.example.demo.Dto.UpdateProductDto;
import com.example.demo.Entities.Category;
import com.example.demo.Entities.Product;
import com.example.demo.Entities.Seller;
import com.example.demo.services.Impl.CategoryServiceImpl;
import com.example.demo.services.Impl.ProductServiceImpl;
import com.example.demo.services.Impl.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/seller")
public class SellerControllerImpl {
    private final ProductServiceImpl productService;
    private final CategoryServiceImpl categoryService;
    private final SellerServiceImpl sellerService;

    @Autowired
    public SellerControllerImpl(ProductServiceImpl productService, CategoryServiceImpl categoryService, SellerServiceImpl sellerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.sellerService = sellerService;
    }

    @PutMapping("/updateProduct/{productID}")
    public String updateProduct(@RequestBody UpdateProductDto productDto, @PathVariable int productID) {
        Product product = productService.getProductById(productID);

        if (product == null) {
            return "Product not found";
        }
        Category category = categoryService.findByName(productDto.getCategoryName());
        if (category == null) {
            return "Category does not exists";
        }
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        product.setPhotoURL(productDto.getPhotoURL());
        product.setTitle(productDto.getName());
        product.setDescription(productDto.getDescription());
        productService.updateProduct(product);
        return "OK";
    }

    @GetMapping("/getProduct/{productID}")
    public Product getProduct(@PathVariable int productID) {
        Product product = productService.getProductById(productID);
        if (product == null) {
            return null;
        }
        return product;

    }

    @GetMapping("/getProducts/{sellerId}")
    public List<ProductDto> getProducts(@PathVariable int sellerId) {
        Seller seller = sellerService.getSellerById(sellerId);
        if (seller == null) {
            return null;

        }
        return productService.getSellerProducts(sellerId);

    }
    @DeleteMapping("/deleteSeller/{sellerID}")
    public String deleteSeller(@PathVariable int sellerID) {
        if (!sellerService.existsById(sellerID)) {
            return "Seller does not found";
        }
        sellerService.deleteSeller(sellerID);
        return "OK";

    }

/*    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        String username = principal.getName();
        Seller user = sellerService.getUser(username);

        UserProfileView userProfileView = new UserProfileView(
                username,
                user.getEmail(),
                user.getFullName(),
                user.getAge()
        );

        model.addAttribute("user", userProfileView);

        return "profile";
    }*/


}
