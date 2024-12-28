package com.example.demo.services;

import com.example.demo.Dto.comment.CommentDto;
import com.example.demo.Entities.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    void updateComment(Comment comment);

    void deleteComment(Comment comment);

    List<CommentDto> getAllProductComments(int productId);
    Comment getCommentById(int commentID);



}
