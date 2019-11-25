package com.ga.commentapi.commentapi.service;

import com.ga.commentapi.commentapi.model.commentModel;
import org.springframework.http.HttpStatus;


public interface CommentService {
    public commentModel createComment(long postId, commentModel comment, String id, String username);
    public HttpStatus deleteComment(long commentId);
    public void deleteCommentByPostId(String message);
    public Iterable<commentModel> getCommentsByPostId(long postId);
    public Iterable<commentModel> getCommentsByUsername(String username);

}
