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

    @PostMapping("/post/{postId}")
    public Comment createComment(@PathVariable long postId, @RequestBody Comment comment, @RequestHeader("username") String username, @RequestHeader("userId") String id) {
        return commentService.createComment(postId, comment, id);
    }

    @DeleteMapping("/{commentId}")
    public HttpStatus deleteComment(@PathVariable long commentId, @RequestHeader("username") String username, @RequestHeader("userId") String id) {
        return commentService.deleteComment(commentId, id);
    }

    @GetMapping("/post/{postId}/comment")
    public Iterable<Comment> getCommentsByPostId(@PathVariable long postId)
    {
        return commentService.getCommentsByPostId(postId);
    }

}