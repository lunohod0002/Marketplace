package com.example.demo.controllers.Impl;

import com.example.demo.Entities.Comment;
import com.example.demo.Entities.Customer;
import com.example.demo.Entities.Product;
import com.example.demo.services.Impl.CommentServiceImpl;
import com.example.demo.services.Impl.CustomerServiceImpl;
import com.example.demo.services.Impl.ProductServiceImpl;
import org.example.input.CommentInputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentControllerImpl {
    private final CommentServiceImpl commentService;
    private final ProductServiceImpl productService;
    private final CustomerServiceImpl customerService;
    @Autowired
    public CommentControllerImpl(CommentServiceImpl commentService, ProductServiceImpl productService, CustomerServiceImpl customerService) {
        this.commentService = commentService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @PostMapping("/addComment/{productID}")
    public String addComment(@PathVariable("productID") int productID, @RequestParam int customerID, @RequestBody CommentInputModel commentDto) {
        Product product=productService.getProductById(productID);
        if (product==null){
            return "Product not found";
        }
        Customer customer=customerService.getCustomerById(customerID);
        if (customer==null){
            return "Customer not found";
        }

        Comment comment=new Comment(commentDto.getText(),commentDto.getTitle(),commentDto.getMark(),product,customer);

        commentService.addComment(comment);
        return "OK";
    }

    @PutMapping("/changeComment/{commentID}")

    public String changeComment(@PathVariable("commentID") int commentID, @RequestBody CommentInputModel commentInputModel) {
        Comment comment=commentService.getCommentById(commentID);
        if (comment==null){
            return "Comment not found";
        }
        comment.setMark(commentInputModel.getMark());
        comment.setText(commentInputModel.getText());
        comment.setTitle(commentInputModel.getTitle());
        commentService.updateComment(comment);
        return "OK";
    }
    @DeleteMapping("/deleteComment/{commentID}")

    public String deleteComment(@PathVariable("commentID") int commentID) {
        Comment comment=commentService.getCommentById(commentID);
        if (comment==null){
            return "Comment not found";
        }
        commentService.deleteComment(comment);
        return "OK";
    }


}
