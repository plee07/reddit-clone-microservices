package com.ga.commentapi.commentapi.controller;


import com.ga.commentapi.commentapi.model.commentModel;
import com.ga.commentapi.commentapi.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.RestResource;
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
    @RestResource(path = "/post/{postId}")
    @ApiOperation("Creates your comments")
    public commentModel createComment(@PathVariable long postId, @RequestBody commentModel comment, @RequestHeader("username") String username, @RequestHeader("userId") String id) {
        return commentService.createComment(postId, comment, id, username);
    }

    @DeleteMapping("/{commentId}")
    @RestResource(path = "/{commentId}")
    @ApiOperation("Deletes your comment")
    public HttpStatus deleteComment(@PathVariable long commentId) {
        return commentService.deleteComment(commentId);
    }

    @GetMapping("/post/{postId}/comment")
    @RestResource(path = "/post/{postId}/comment")
    @ApiOperation("Find comments by the postId")
    public Iterable<commentModel> getCommentsByPostId(@PathVariable long postId)
    {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/user")
    @RestResource(path = "/user")
    @ApiOperation("Find comments by user's username")
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
