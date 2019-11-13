package com.ga.commentapi.commentapi.service;

import com.ga.commentapi.commentapi.model.Comment;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface CommentService {
    public Comment createComment(long postId, Comment comment, String jwToken);
    public HttpStatus deleteComment(long commentId, String jwToken);
    public Iterable<Comment> getCommentsByPostId(long postId);
    public List<Comment> getCommentsByUser(String jwToken);
}