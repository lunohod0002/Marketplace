package com.example.demo;

import com.example.demo.Dto.ProductDto;
import com.example.demo.Entities.*;
import com.example.demo.Entities.enums.UserRoles;
import com.example.demo.Repositories.*;
import com.example.demo.services.Impl.SellerServiceImpl;
import com.example.demo.services.ProductService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.javafaker.Faker;
import org.example.input.ProductCreateInputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class WebPracticApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebPracticApplication.class, args);
    }
    //@Component

    public class Init implements CommandLineRunner {

        private final ProductRepository productRepository;

        private final DealProductRepository dealProductRepository;

        private final CategoryRepository categoryRepository;

        private final CommentRepository commentRepository;

        private final CustomerRepository customerRepository;

        private final DealRepository dealRepository;

        private final SellerRepository sellerRepository;

        private final UserRoleRepository userRoleRepository;
        private final UserRepository userRepository;

        private final PasswordEncoder passwordEncoder;

        Faker fakerRUS = new Faker(new Locale("ru"));
        Faker fakerUSA = new Faker(new Locale("en-US"));

        public Init(ProductRepository productRepository, DealProductRepository dealProductRepository, CategoryRepository categoryRepository, CommentRepository commentRepository, CustomerRepository customerRepository, DealRepository dealRepository, SellerRepository sellerRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
            this.productRepository = productRepository;
            this.dealProductRepository = dealProductRepository;
            this.categoryRepository = categoryRepository;
            this.commentRepository = commentRepository;
            this.customerRepository = customerRepository;
            this.dealRepository = dealRepository;
            this.sellerRepository = sellerRepository;
            this.userRoleRepository = userRoleRepository;
            this.passwordEncoder = passwordEncoder;
            this.userRepository=userRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            var customer1Role = new Role(UserRoles.CUSTOMER);
            var seller1Role = new Role(UserRoles.SELLER);
            userRoleRepository.save(customer1Role);
            userRoleRepository.save(seller1Role);

            List<String> seasons = new ArrayList<>(Arrays.asList("Осень", "Зима", "Весна", "Лето"));

            for (int i = 0; i < 100; i++) {
                var sellerRole = userRoleRepository.
                        findRoleByName(UserRoles.SELLER).orElseThrow();

                Seller seller = new Seller("Seller_%d".formatted(i),  "seller%d@example.com".formatted(i), passwordEncoder.encode("123456789"));
                seller.setRoles(List.of(sellerRole));

                userRepository.save(seller);
                var userRole = userRoleRepository.
                        findRoleByName(UserRoles.CUSTOMER).orElseThrow();

                Customer customer = new Customer("Customer_%d".formatted(i),  "customer%d@example.com".formatted(i),passwordEncoder.encode("12345678"));
                customer.setRoles(List.of(userRole));
                userRepository.save(customer);
                Category category = new Category(fakerRUS.name().name(), fakerRUS.book().title(), seasons.get(fakerRUS.random().nextInt(0, 3)));
                categoryRepository.save(category);

                Product product = new Product(fakerRUS.book().title(), fakerRUS.gameOfThrones().quote(), "https://ir.ozone.ru/s3/multimedia-1-p/wc600/7209533977.jpg", 10, fakerRUS.random().nextInt(100000), seller, category);
                productRepository.save(product);
                Comment comment = new Comment(fakerRUS.name().name(), fakerRUS.book().title(),fakerRUS.random().nextInt(1, 5), product, customer);
                commentRepository.save(comment);
                Deal deal = new Deal(fakerRUS.random().nextInt(500, 10000),  customer);
                dealRepository.save(deal);
                DealProduct dealProduct = new DealProduct(fakerRUS.random().nextInt(1, 100), product);
                dealProduct.setDeal(deal);
                dealProductRepository.save(dealProduct);

            }


        }
    }
}



