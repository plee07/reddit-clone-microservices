package com.ga.commentapi.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ga.commentapi.model.CommentModel;
import com.ga.commentapi.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}")
    @RestResource(path = "/post/{postId}")
    @ApiOperation("Creates your comments")
    public CommentModel createComment(@Param("postId, comment, userid, username")@PathVariable long postId, @Valid @RequestBody CommentModel comment, @RequestHeader("username") String username, @RequestHeader("userId") String id) throws JsonProcessingException {
        return commentService.createComment(postId, comment, id, username);
    }

    @DeleteMapping("/{commentId}")
    @RestResource(path = "/{commentId}")
    @ApiOperation("Deletes your comment")
    public HttpStatus deleteComment(@Param("commentId")@PathVariable long commentId) {
        return commentService.deleteComment(commentId);
    }

    @GetMapping("/post/{postId}/comment")
    @RestResource(path = "/post/{postId}/comment")
    @ApiOperation("Find comments by the postId")
    public Iterable<CommentModel> getCommentsByPostId(@Param("postId")@PathVariable long postId)
    {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/user")
    @RestResource(path = "/user")
    @ApiOperation("Find comments by user's username")
    public Iterable<CommentModel> getCommentByUsername(@Param("username")@RequestHeader("username") String username)
    {
        return commentService.getCommentsByUsername(username);
    }



}
