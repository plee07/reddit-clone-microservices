package com.ga.commentapi.commentapi.controller;


import com.ga.commentapi.commentapi.model.commentModel;
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
    public commentModel createComment(@PathVariable long postId, @RequestBody commentModel comment, @RequestHeader("username") String username, @RequestHeader("userId") String id) {
        return commentService.createComment(postId, comment, id, username);
    }

    @DeleteMapping("/{commentId}")
    public HttpStatus deleteComment(@PathVariable long commentId) {
        return commentService.deleteComment(commentId);
    }

    @GetMapping("/post/{postId}/comment")
    public Iterable<commentModel> getCommentsByPostId(@PathVariable long postId)
    {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/user")
    public Iterable<commentModel> getCommentByUsername(@RequestHeader("username") String username)
    {
        return commentService.getCommentsByUsername(username);
    }

//    @GetMapping("/deleteBy/{postId}")
//    public String deleteCommentByPostId(@PathVariable long postId){
//        commentService.deleteCommentByPostId(postId);
//        return String.valueOf(postId);
//    }

}
