package com.example.demo.controllers.Impl;

import com.example.demo.Entities.*;
import com.example.demo.Repositories.*;
import org.example.controllers.UserController;
import org.example.input.ProductsSearchForm;
import org.example.viewmodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;


@Controller()
@RequestMapping("")

public class UserControllerImpl implements UserController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired

    private SellerRepository sellerRepository;

    @Autowired

    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @GetMapping("/profile")

    public String getUserProfile(@ModelAttribute("form") ProductsSearchForm form, Model model, Principal principal) {
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 8;
        var category = form.category() != null ? form.category() : "";

        var priceFilter = form.priceFilter() != null ? form.priceFilter() : "";
        var sellerFilter = form.sellerFilter() != null ? form.sellerFilter() : "";

        var dateFilter = form.season() != null ? form.season() : "";
        form = new ProductsSearchForm(searchTerm, priceFilter, sellerFilter, dateFilter, page, size, category);

        var categories = Arrays.asList("Все категории", "Электроника", "Одежда", "Концтовары", "Обувь");
        model.addAttribute("categories", categories);
        User user = userRepository.findByUsername(principal.getName()).orElse(null);
        if (user != null) {
            if (user.getRoles().get(0).getName().name().equals("SELLER")) {
                Seller seller = sellerRepository.findByUsername(principal.getName()).orElse(null);
                SellerViewModel viewModel = new SellerViewModel(seller.getEmail(), seller.getUsername(), seller.getRating());
                List<Product> products = productRepository.findSellerProducts(seller.getId());
                List<SellerProductViewModel> productViewModels = products.stream().map(p -> new SellerProductViewModel(p.getId(), p.getTitle(), p.getDescription(), p.getPhotoURL(), p.getQuantity(), p.getPrice(), p.getCategory().getName())).toList();
                model.addAttribute("user", viewModel);
                model.addAttribute("products", productViewModels);

                model.addAttribute("form", form);
                return "seller-profile";
            }
            Customer customer = customerRepository.findByUsername(principal.getName()).orElse(null);
            CustomerViewModel viewModel = new CustomerViewModel(customer.getEmail(), customer.getUsername(), customer.getAge(), customer.getBalance());
            model.addAttribute("user", viewModel);
            model.addAttribute("form", form);
            return "profile";

        }
        return "redirect:/users/login";

    }
    @Override
    @GetMapping("/deals")
    public String getUserDeals(@ModelAttribute("form") ProductsSearchForm form, Model model, Principal principal) {
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 8;
        var category = form.category() != null ? form.category() : "";

        var priceFilter = form.priceFilter() != null ? form.priceFilter() : "";
        var sellerFilter = form.sellerFilter() != null ? form.sellerFilter() : "";

        var dateFilter = form.season() != null ? form.season() : "";
        form = new ProductsSearchForm(searchTerm, priceFilter, sellerFilter, dateFilter, page, size, category);

        Customer customer = customerRepository.findByUsername(principal.getName()).orElse(null);
        var categories = Arrays.asList("Все категории", "Электроника", "Одежда", "Концтовары", "Обувь");

        List<Deal> deals = customer.getDeals();
        List<DealViewModel> dealViewModels = deals.stream().map(d -> new DealViewModel(d.getTotalCost(), d.getDateOfCreation())).toList();
        DealListViewModel viewModel = new DealListViewModel(dealViewModels);
        //     System.out.println(viewModel);
        model.addAttribute("categories", categories);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "deals";
    }
}
