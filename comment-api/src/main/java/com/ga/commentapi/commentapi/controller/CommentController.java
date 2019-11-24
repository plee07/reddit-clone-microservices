package com.ga.commentapi.commentapi.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ga.commentapi.commentapi.model.Comment;
import com.ga.commentapi.commentapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}")
    public Comment createComment(@PathVariable long postId, @RequestBody Comment comment, @RequestHeader("username") String username, @RequestHeader("userId") String id) throws JsonProcessingException {
        return commentService.createComment(postId, comment, id, username);
    }

    @DeleteMapping("/{commentId}")
    public HttpStatus deleteComment(@PathVariable long commentId) {
        return commentService.deleteComment(commentId);
    }

    @GetMapping("/post/{postId}/comment")
    public Iterable<Comment> getCommentsByPostId(@PathVariable long postId)
    {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/user/{username}")
    public Iterable<Comment> getCommentByUserId(@PathVariable Long userId, @RequestHeader("username") String username)
    {
        return commentService.getCommentsByUserId(username, userId);
    }

    @GetMapping("/user")
    public Iterable<Comment> getCommentByUsername(@RequestHeader("username") String username)
    {
        return commentService.getCommentsByUsername(username);
    }



}
