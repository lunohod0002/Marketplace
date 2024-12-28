package com.example.demo.services.Impl;

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
        return new ProductDto(p.getId(), p.getTitle(), p.getDescription(), p.getPhotoURL(), p.getPrice(), p.getQuantity(), p.getCategory().getName(),p.getCategory().getSeason(), p.getSeller().getUsername(), p.getComments().stream().map(comment -> new CommentDto(comment.getTitle(), comment.getText(), comment.getDateAdded(), comment.getMark(), comment.getCustomer().getUsername())).toList());
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
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("title"));
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
                    // Если парсинг не удался, можно оставить minPrice и maxPrice равными null
                    // Либо обработать по-другому в зависимости от вашей логики
                }
            }
        }
        if (searchTerm != null && !searchTerm.isEmpty()) {
            productPage = productRepository.findByTitleContainingIgnoreCase(searchTerm, pageable);
        }
        else {
            productPage = productRepository.findAllProductsByFiltersAndCategory(
                    pageable,
                    minPrice,
                    maxPrice,
                    sellerFilter != null && !sellerFilter.isEmpty() ? sellerFilter : null,
                    category != null && !category.isEmpty() ? category : null,
                    season != null && !season.isEmpty() ? season : null

            );
        }
        return productPage.map(product -> new ProductInfoDto(product.getId(), product.getTitle(), product.getPhotoURL(), product.getPrice(), product.getComments().stream().map(comment -> new CommentDto(comment.getTitle(), comment.getText(), comment.getDateAdded(), comment.getMark(), comment.getCustomer().getUsername())).toList()));

/*        List<ProductInfoDto> productInfoDtos=new ArrayList<>();
        for (Product product : productPage.getContent()) {
            System.out.println(product.getPrice() + " "+ priceFilter);
            if (priceFilter.equals("0-500")) {
                if (product.getPrice() <= 500){
                    productInfoDtos.add(new ProductInfoDto(product.getId(),product.getTitle(), product.getPhotoURL(), product.getPrice(),product.getComments().stream().map(comment -> new CommentDto(comment.getTitle(),comment.getText(),comment.getDateAdded(),comment.getMark(), comment.getCustomer().getUsername())).toList()));
                }
            }
            if (priceFilter.equals("500-1000")) {
                if (product.getPrice() <= 1000 && product.getPrice() >= 500){
                    productInfoDtos.add(new ProductInfoDto(product.getId(),product.getTitle(), product.getPhotoURL(), product.getPrice(),product.getComments().stream().map(comment -> new CommentDto(comment.getTitle(),comment.getText(),comment.getDateAdded(),comment.getMark(), comment.getCustomer().getUsername())).toList()));
                }
            }
            if (priceFilter.equals("1000-5000")) {
                if (product.getPrice() <= 5000 && product.getPrice() >= 1000){
                    productInfoDtos.add(new ProductInfoDto(product.getId(),product.getTitle(), product.getPhotoURL(), product.getPrice(),product.getComments().stream().map(comment -> new CommentDto(comment.getTitle(),comment.getText(),comment.getDateAdded(),comment.getMark(), comment.getCustomer().getUsername())).toList()));
                }
            }
            if (priceFilter.equals("5000")) {
                if (product.getPrice() >= 5000 ){
                    productInfoDtos.add(new ProductInfoDto(product.getId(),product.getTitle(), product.getPhotoURL(), product.getPrice(),product.getComments().stream().map(comment -> new CommentDto(comment.getTitle(),comment.getText(),comment.getDateAdded(),comment.getMark(), comment.getCustomer().getUsername())).toList()));
                }*/

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
       /* Page<ProductInfoDto> products = productRepository.findAllProductsByCategory(categoryName);
        for (ProductInfoDto product : products) {
            List<Comment> comments = commentRepository.findCommentsByProductIdSorted(product.id());
            product.comments()=comments;
        }*/
        //return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
        return null;
    }

    @Override
    public List<ProductDto> getAllMostSoldProducts() {
        return List.of();
    }

    /*@Override
    public List<ProductDto> getAllMostSoldProducts() {
        List<Product> products = productRepository.findAllMostSoldProducts();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDtoList.add(productDto);
        }
        return productDtoList;
    }*/

    @Override
    public List<ProductDto> getAllMostSoldProductsInThisSeason(String seasonName) {
        List<Product> products = productRepository.findAllMostSoldProductsInThisSeason(seasonName);
        return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
    }
}
