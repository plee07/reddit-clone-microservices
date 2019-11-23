package com.ga.commentapi.commentapi.service;

import com.ga.commentapi.commentapi.model.commentModel;
import org.springframework.http.HttpStatus;


public interface CommentService {
    public commentModel createComment(Long postId, commentModel comment, String id, String username);
    public HttpStatus deleteComment(long commentId);
    public Iterable<commentModel> deleteCommentByPostId(String message);
    public Iterable<commentModel> getCommentsByPostId(long postId);
    public Iterable<commentModel> getCommentsByUserId(String username, Long userId);
    public Iterable<commentModel> getCommentsByUsername(String username);
}
