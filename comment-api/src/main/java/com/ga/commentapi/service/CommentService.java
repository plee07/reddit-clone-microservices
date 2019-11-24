package com.ga.commentapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ga.commentapi.model.Comment;
import org.springframework.http.HttpStatus;


public interface CommentService {
    public Comment createComment(Long postId, Comment comment, String id, String username) throws JsonProcessingException;
    public HttpStatus deleteComment(long commentId);
    public void deleteCommentByPostId(String message);
    public Iterable<Comment> getCommentsByPostId(long postId);
    public Iterable<Comment> getCommentsByUserId(String username, Long userId);
    public Iterable<Comment> getCommentsByUsername(String username);
}
