package com.example.demo.Dto;

import com.example.demo.Dto.comment.CommentDto;

import java.util.List;

public record ProductInfoDto (
        int id,
        String title,
        String photoURL,
        int price,
        List<CommentDto> comments)
{}