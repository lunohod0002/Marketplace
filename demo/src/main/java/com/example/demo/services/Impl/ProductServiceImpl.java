package com.example.demo.services.Impl;

import com.example.demo.Dto.ProductWithCommentsDTO;
import com.example.demo.Dto.comment.CommentDto;
import com.example.demo.Dto.DealProductDto;
import com.example.demo.Dto.ProductDto;
import com.example.demo.Dto.ProductInfoDto;
import com.example.demo.Entities.*;
import com.example.demo.Repositories.*;
import com.example.demo.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CommentRepository commentRepository;
    private final DealRepository dealRepository;
    private final DealProductRepository dealProductRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ProductServiceImpl(ProductRepository categoryRepository, CommentRepository commentRepository, CustomerRepository customerRepository, DealRepository dealRepository, DealProductRepository dealProductRepository) {
        this.productRepository = categoryRepository;
        this.commentRepository = commentRepository;

        this.dealRepository = dealRepository;
        this.dealProductRepository = dealProductRepository;
    }

    @Override
    public void addProduct(Product product) {
        Product product2 = productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(int productID) {
        productRepository.deleteProduct(productID);
    }

    @Override
    public boolean existsById(int productID) {
        return productRepository.existsById(productID);
    }


    @Override
    public List<ProductDto> getSellerProducts(int sellerId) {
        List<Product> products = productRepository.findSellerProducts(sellerId);
        return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();

    }

    @Override
    public void changeProductQuantity(int productID, int quantity) {
        productRepository.changeQuantity(productID, quantity);
    }


    @Override
    public Product getProductByName(String productTitle) {
        Optional<Product> product = productRepository.findByName(productTitle);
        return product.orElse(null);
    }

    @Override
    public ProductDto getProductDtoById(int productID) {
        Optional<Product> product = productRepository.findById(productID);
        if (product == null) {
            return null;
        }
        Product p = product.orElse(null);
        return new ProductDto(p.getId(), p.getTitle(), p.getDescription(), p.getPhotoURL(), p.getPrice(), p.getQuantity(), p.getCategory().getName(), p.getCategory().getSeason(), p.getSeller().getUsername(), p.getComments().stream().map(comment -> new CommentDto(comment.getTitle(), comment.getText(), comment.getDateAdded(), comment.getMark(), comment.getCustomer().getUsername())).toList());
    }

    @Override
    public Product getProductById(int productID) {
        Optional<Product> product = productRepository.findById(productID);
        if (product == null) {
            return null;
        }
        return product.orElse(null);
    }

    @Override
    public Page<ProductInfoDto> getAllProducts(String searchTerm, String priceFilter, String category, String sellerFilter, String season, int page, int size) {
        Page<Product> productPage;
        Integer minPrice = null;
        Integer maxPrice = null;
        System.out.println(searchTerm);

        // Обработка priceFilter
        if (priceFilter != null && !priceFilter.isEmpty()) {
            String[] parts = priceFilter.split("-");
            if (parts.length == 2) {
                try {
                    minPrice = Integer.parseInt(parts[0].trim());
                    maxPrice = Integer.parseInt(parts[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Exception");
                }
            }
        }
            if (searchTerm != null && !searchTerm.isEmpty() || sellerFilter != null && !sellerFilter.isEmpty() || category != null && !category.isEmpty() || season != null && !season.isEmpty() || maxPrice!=null || minPrice!=null)  {
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by("title"));

            productPage = productRepository.findAllProductsByFiltersAndCategoryAndTitleIgnoreCase(
                    pageable,
                    minPrice,
                    maxPrice,
                    sellerFilter != null && !sellerFilter.isEmpty() ? sellerFilter : null,
                    category != null && !category.isEmpty() ? category : null,
                    season != null && !season.isEmpty() ? season : null,
                    searchTerm

            );
            productPage.forEach(product -> {
                List<Comment> comments = productRepository.findAllCommentsByProduct(
                        product.getId(),
                        PageRequest.of(0, 3)
                );
                product.setComments(comments); // Если у сущности Product есть поле comments
            });

            return productPage.map(product -> new ProductInfoDto(product.getId(), product.getTitle(), product.getPhotoURL(), product.getPrice(), product.getComments().stream().map(comment -> new CommentDto(comment.getTitle(), comment.getText(), comment.getDateAdded(), comment.getMark(), comment.getCustomer().getUsername())).toList()));


        }
        else {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Product> products = productRepository.findTopSellingProducts(pageable);
            products.forEach(product -> {
                List<Comment> comments = productRepository.findAllCommentsByProduct(
                        product.getId(),
                        PageRequest.of(0, 3)
                );
                product.setComments(comments); // Если у сущности Product есть поле comments
            });
            return products.map(product -> new ProductInfoDto(product.getId(), product.getTitle(), product.getPhotoURL(), product.getPrice(), product.getComments().stream().map(comment -> new CommentDto(comment.getTitle(), comment.getText(), comment.getDateAdded(), comment.getMark(), comment.getCustomer().getUsername())).toList()));


        }
// В случае, если productPage не был установлен в предыдущих условиях, выполняем преобразование для него
    }



    @Override
    public void buyProduct(Product product, int quantity, Customer customer) {
        int totalCost = quantity * product.getPrice();
        Deal deal = new Deal(totalCost, customer);
        dealRepository.save(deal);

        DealProduct dealProduct = new DealProduct(quantity, product);
        dealProduct.setDeal(deal);
        dealProductRepository.save(dealProduct);

    }

    @Override
    public void buyProducts(List<DealProductDto> products, Customer customer) {
        int totalCost = 0;
        Deal deal = new Deal(totalCost, customer);
        List<DealProduct> dealProducts = new ArrayList<>();
        for (DealProductDto product : products) {
            Optional<Product> product1 = productRepository.findByName(product.getproductTitle());
            if (product1.isPresent()) {
                totalCost += product1.orElse(null).getPrice() * product.getQuantity();
                DealProduct dealProduct = new DealProduct(product.getQuantity(), product1.orElse(null));
                dealProduct.setDeal(deal);
                dealProducts.add(dealProduct);
            }

        }
        deal.setTotalCost(totalCost);
        dealRepository.save(deal);
        dealProductRepository.saveAll(dealProducts);

    }


    @Override
    public Page<ProductInfoDto> getAllProductsByCategory(String searchTerm, int page, int size, String categoryName) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("title"));
        Page<Product> productPage = searchTerm != null
                ? productRepository.findByTitleContainingIgnoreCase(searchTerm, pageable)
                : productRepository.findAllMostSoldProductsByCategory(pageable, categoryName);
        return productPage.map(product -> new ProductInfoDto(product.getId(), product.getTitle(), product.getPhotoURL(), product.getPrice(), product.getComments().stream().map(comment -> new CommentDto(comment.getTitle(), comment.getText(), comment.getDateAdded(), comment.getMark(), comment.getCustomer().getUsername())).toList()));
    }

    @Override
    public List<ProductDto> getAllProductsByCategoryWithThreeLastComments(String categoryName) {
            return null;
    }

    @Override
    public List<ProductDto> getAllMostSoldProducts() {
        return List.of();
    }


    @Override
    public List<ProductDto> getAllMostSoldProductsInThisSeason(String seasonName) {
        List<Product> products = productRepository.findAllMostSoldProductsInThisSeason(seasonName);
        return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
    }
}
