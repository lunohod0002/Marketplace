package com.example.demo.Repositories;

import com.example.demo.Entities.Comment;
import com.example.demo.Entities.DealProduct;
import com.example.demo.Entities.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealProductRepository extends JpaRepository<DealProduct, Long> {

    @Query("SELECT p " +
            "FROM DealProduct dp JOIN dp.product p " +
            "GROUP BY p.id, p.title, p.photoURL, p.price " +
            "ORDER BY SUM(dp.quantity) DESC")
    Page<Product> findTopSellingProducts(Pageable pageable);

    @Query("SELECT c " +
            "FROM Comment c " +
            "WHERE c.product.id = :productId " +
            "ORDER BY c.dateAdded DESC limit 3")
    List<Comment> findAllCommentsByProduct(@Param("productId") Integer productId);

    @Query(value = "SELECT p.*, " +
            "       (SELECT ARRAY(c ORDER BY c.date_added DESC LIMIT 3) " +
            "        FROM comment c " +
            "        WHERE c.product_id = p.id) AS recent_comments " +
            "FROM deal_product dp " +
            "JOIN product p ON dp.product_id = p.id " +
            "GROUP BY p.id, p.title, p.photo_url, p.price " +
            "ORDER BY SUM(dp.quantity) DESC",
            nativeQuery = true)
    List<Object[]> findTopSellingProductsWithComments();

}

