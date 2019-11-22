package com.ga.commentapi.commentapi.service;

import com.ga.commentapi.commentapi.model.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public interface CommentService {
    public Comment createComment(Long postId, Comment comment, String id, String username);
    public HttpStatus deleteComment(long commentId);
    public Iterable<Comment> deleteCommentByPostId(String message);
    public Iterable<Comment> getCommentsByPostId(long postId);
    public Iterable<Comment> getCommentsByUserId(String username, Long userId);
    public Iterable<Comment> getCommentsByUsername(String username);
}
