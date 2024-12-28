package com.example.demo.Repositories;

import com.example.demo.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT pr FROM Product pr where pr.title=:productTitle")
    Optional<Product> findByName(@Param("productTitle") String productTitle);

    @Query("UPDATE Product pr SET pr.quantity= pr.quantity - :changeQuantity where pr.id=:productID")
    void changeQuantity(@Param("productID") int productID, @Param("changeQuantity") int changeQuantity);

    @Modifying
    @Transactional
    @Query("UPDATE Product pr SET pr.deleted= true  where pr.id=:productID")
    void deleteProduct(@Param("productID") int productID);

    Page<Product> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("SELECT sl.products FROM Seller sl where sl.id=:seller_id ")
    List<Product> findSellerProducts(@Param("seller_id") int sellerId);

    @Query("SELECT dp.product   FROM DealProduct dp  GROUP BY dp.product ORDER BY SUM(dp.quantity) DESC")
    Page<Product> findAllMostSoldProducts(Pageable pageable);
    @Query("SELECT p FROM Product p " +
            "WHERE (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:sellerName IS NULL OR p.seller.username = :sellerName) " +
            "AND (:categoryName IS NULL OR p.category.name = :categoryName)"+
            "AND (:categoryName IS NULL OR p.category.name = :categoryName)"+
            "AND (:season IS NULL OR p.category.season = :season)")
    Page<Product> findAllProductsByFiltersAndCategory(Pageable pageable, @Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice,  @Param("sellerName") String sellerName,@Param("categoryName") String categoryName,@Param("season") String season );


    @Query("select p from Product p join p.category c where c.name=:categoryName ORDER BY p.id DESC")
    Page<Product> findAllProductsByCategory(Pageable pageable, @Param("categoryName") String categoryName);

    @Query("SELECT dp.product   FROM DealProduct dp  JOIN dp.product.category ct WHERE ct.name=:categoryName GROUP BY dp.product ORDER BY SUM(dp.quantity) DESC")
    Page<Product> findAllMostSoldProductsByCategory(Pageable pageable, @Param("categoryName") String categoryName);

    @Query("SELECT dp.product\n" +
            "FROM DealProduct dp\n" +
            "JOIN dp.product p\n" +
            "JOIN p.category ct\n" +
            "JOIN dp.deal d\n" +
            "WHERE ct.season = :seasonName\n" +
            "GROUP BY dp.product\n" +
            "ORDER BY MAX(dp.quantity) DESC, d.dateOfCreation DESC")
    List<Product> findAllMostSoldProductsInThisSeason(@Param("seasonName") String seasonName);


}