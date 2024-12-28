package com.example.demo.Dto;

import com.example.demo.Dto.comment.CommentDto;
import org.example.viewmodel.CommentViewModel;

import java.util.List;

public record ProductDto(
        int id,
        String title,
        String description,

        String photoURL,
        int price,
        int quantity,
        String categoryName,
        String season,
        String sellerUsername,
        List<CommentDto> comments

) {}