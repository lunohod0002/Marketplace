package com.example.demo.services.Impl;

import com.example.demo.Dto.comment.CommentDto;
import com.example.demo.Entities.Comment;
import com.example.demo.Repositories.CommentRepository;
import com.example.demo.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private  final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> getAllProductComments(int productId) {
        return null;
    }

    @Override
    public Comment getCommentById(int commentID) {
        Optional<Comment> comment=commentRepository.findById(commentID);
        return comment.orElse(null);
    }


}
