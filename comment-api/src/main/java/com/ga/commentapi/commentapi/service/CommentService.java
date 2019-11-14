package com.ga.commentapi.commentapi.service;

import com.ga.commentapi.commentapi.model.Comment;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface CommentService {
    public Comment createComment(long postId, Comment comment, String id, String username);
    public HttpStatus deleteComment(long commentId);
    public Iterable<Comment> getCommentsByPostId(long postId);
    public Iterable<Comment> getCommentsByUserId(String username, Long userId);
}
