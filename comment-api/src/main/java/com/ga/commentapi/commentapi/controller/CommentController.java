package com.ga.commentapi.commentapi.controller;


import com.ga.commentapi.commentapi.model.Comment;
import com.ga.commentapi.commentapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private CommentService commentService;
    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment/{postId}")
    public Comment createComment(@PathVariable long postId, @RequestBody Comment comment, @RequestHeader(value="Authorization") String jwToken) {
        return commentService.createComment(postId, comment, jwToken);
    }

    @DeleteMapping("/comment/{commentId}")
    public HttpStatus deleteComment(@PathVariable long commentId, @RequestHeader(value="Authorization") String jwToken) {
        return commentService.deleteComment(commentId, jwToken);
    }

    @GetMapping("/post/{postId}/comment")
    public Iterable<Comment> getCommentsByPostId(@PathVariable long postId)
    {
        return commentService.getCommentsByPostId(postId);
    }

}
