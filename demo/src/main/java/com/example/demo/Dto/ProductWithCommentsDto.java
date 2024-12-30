package com.example.demo.Dto;

import com.example.demo.Dto.comment.CommentDto;
import com.example.demo.Entities.Comment;
import com.example.demo.Entities.Product;

import java.util.List;

public class ProductWithCommentsDTO {
    private Product product;
    private List<Comment> lastComments;

    public ProductWithCommentsDTO(Product product, List<Comment> lastComments) {
        this.product = product;
        this.lastComments = lastComments;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Comment> getLastComments() {
        return lastComments;
    }

    public void setLastComments(List<Comment> lastComments) {
        this.lastComments = lastComments;
    }
    // геттеры и сеттеры
}