package com.example.demo.Repositories;

import com.example.demo.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findByTitle(String title);

    @Query("SELECT c FROM Comment c WHERE c.product.id = :productId ORDER BY c.dateAdded ASC")
    List<Comment> findCommentsByProductIdSorted(@Param("productId") int productId);

   // List<Comment> findByDateAbove(LocalDate date);
}