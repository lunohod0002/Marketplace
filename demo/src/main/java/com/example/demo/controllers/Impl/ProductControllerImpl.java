package com.example.demo.controllers.Impl;

import com.example.demo.Dto.*;
import com.example.demo.Entities.*;
import com.example.demo.services.CustomerService;
import com.example.demo.services.Impl.*;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.input.ProductCreateInputModel;
import org.example.input.UserRegisterInputModel;
import org.example.viewmodel.*;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/products")
@EnableCaching
public class ProductControllerImpl {
    private final ProductServiceImpl productService;
    private final CustomerServiceImpl customerService;
    private final CategoryServiceImpl categoryService;
    private final SellerServiceImpl sellerService;

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public ProductControllerImpl(ProductServiceImpl productService, CustomerServiceImpl customerService, CategoryServiceImpl categoryService, SellerServiceImpl sellerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.customerService = customerService;
        this.sellerService = sellerService;
    }
    @GetMapping()
    @Cacheable(value = "/products")
    public String getAllProduct(@ModelAttribute("form") ProductSearchForm form, Model model, Principal principal) {
        LOG.log(Level.INFO, "Show all products for" + principal.getName());
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 8;
        var category = form.category() != null ? form.category() : "";
        var priceFilter = form.priceFilter() != null ? form.priceFilter() : "";
        var sellerFilter = form.sellerFilter() != null ? form.sellerFilter() : "";
        var dateFilter = form.season() != null ? form.season() : "";

        System.out.println(priceFilter
        );
        if (category.equals("Все категории")){
            category="";
        }
        form = new ProductSearchForm(searchTerm,priceFilter,sellerFilter, dateFilter, page, size, category);
        var productsPage = productService.getAllProducts(searchTerm, priceFilter,  category, sellerFilter,dateFilter,page, size);


        var productViewModels = productsPage.stream()

                .map(p -> new ProductInfoViewModel(p.id(), p.title(), p.photoURL(), p.price(), p.comments().stream().map(c -> new CommentViewModel(c.title(), c.text(), c.dateArrival(), c.mark(), c.username())).toList()))
                .toList();

        var viewModel = new ProductListViewModel(
                createBaseViewModel("Топ продаваемых товаров"),
                productViewModels,
                productsPage.getTotalPages()
        );
        var categories = Arrays.asList("Все категории","Электроника", "Одежда", "Канцтовары", "Обувь");

        model.addAttribute("categories", categories);
        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);
        return "product-list";
    }


    public String productsWithComments(Model model) {
        return null;
    }


    @GetMapping("/{productID}")
    public String getProductsByName(@ModelAttribute("form") ProductSearchForm form, @PathVariable(name = "productID") int productID, Model model) {
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 8;
        var category = form.category() != null ? form.category() : "";
        var priceFilter = form.priceFilter() != null ? form.priceFilter() : "";
        var sellerFilter = form.sellerFilter() != null ? form.sellerFilter() : "";

        var season = form.season() != null ? form.season() : "";
        form = new ProductSearchForm(searchTerm,priceFilter,sellerFilter, season, page, size, category);


        var productPage = productService.getProductDtoById(productID);
        LOG.log(Level.INFO, "Show product: " + productPage.toString());

        var productViewModel = new ProductViewModel(productPage.id(), productPage.title(), productPage.description(), productPage.photoURL(), productPage.price(), productPage.quantity(), productPage.categoryName(), productPage.season(), productPage.sellerUsername(), productPage.comments().stream().map(c -> new CommentViewModel(c.title(), c.text(), c.dateArrival(), c.mark(), c.username())).toList());

        var viewModel = new ProductDetailsViewModel(
                createBaseViewModel(productPage.title()),
                productViewModel);
        var categories = Arrays.asList("Все категории","Электроника", "Одежда", "Концтовары", "Обувь");
        model.addAttribute("categories", categories);

        model.addAttribute("model", viewModel);
        model.addAttribute("form", form);

        return "product-details";


    }

    @Transactional
    @PostMapping("/buyProduct/{productID}")
    public String buyProduct(@ModelAttribute("form") ProductSearchForm form, @PathVariable("productID") int productID, Model model, RedirectAttributes redirectAttributes, Principal principal) throws InterruptedException {
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 8;
        var category = form.category() != null ? form.category() : "";
        var priceFilter = form.priceFilter() != null ? form.priceFilter() : "";
        var sellerFilter = form.sellerFilter() != null ? form.sellerFilter() : "";

        var dateFilter = form.season() != null ? form.season() : "";
        form = new ProductSearchForm(searchTerm,priceFilter,sellerFilter, dateFilter, page, size, category);

        Thread.sleep(3000);
        Customer customer = customerService.getCustomerByUsername(principal.getName());
        Product product = productService.getProductById(productID);
        if (customer.getBalance() < product.getPrice()) {
            redirectAttributes.addFlashAttribute("error", "У вас недостаточно средств для покупки этого продукта.");
            return "redirect:/products";
        } else if (product.getQuantity() <= 0) {
            redirectAttributes.addFlashAttribute("error", "Товар закончился.");
            return "redirect:/products";

        }

        productService.buyProduct(product, 1, customer);
        product.setQuantity(product.getQuantity() - 1);
        customerService.updateBalance(customer, product.getPrice());
        productService.updateProduct(product);
        System.out.println("Получен товар");

        model.addAttribute("form", form);
        return "redirect:/products";
    }


    @PostMapping("/buyProducts/{username}")
    public String buyProducts(@PathVariable("username") String username, @RequestBody CreateDealDto deal, Model model) {
        var customer = customerService.getCustomerByUsername(username);
        List<DealProductDto> productDtos = deal.getProducts();
        productService.buyProducts(productDtos, customer);
        return "OK";


    }

    @ModelAttribute("productCreateModel")
    public ProductCreateInputModel initForm() {
        return new ProductCreateInputModel();
    }

    @GetMapping("/addProduct")
    public String addProductPage(@ModelAttribute("form") ProductSearchForm form, Model model) {
        var searchTerm = form.searchTerm() != null ? form.searchTerm() : "";
        var page = form.page() != null ? form.page() : 1;
        var size = form.size() != null ? form.size() : 3;
        var categories = Arrays.asList("Все категории","Электроника", "Одежда", "Концтовары", "Обувь");

        model.addAttribute("categories", categories);
        model.addAttribute("form", form);

        return "product-add";
    }

    @PostMapping("/createProduct")
    public String addProduct(@ModelAttribute ProductCreateInputModel productDto, Principal principal) {
        Category category = categoryService.findByName(productDto.getCategory());
        if (category == null) {
            return "Category does not exists";
        }
        Seller seller = sellerService.getSellerByUsername(principal.getName());
        Product product = new Product(productDto.getTitle(), productDto.getDescription(), productDto.getPhotoURL(), productDto.getQuantity(), productDto.getPrice(), seller, category);
        productService.addProduct(product);

        return "redirect:/products/addProduct";
    }

    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }


}
