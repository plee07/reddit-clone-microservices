package com.ga.commentapi.service;

import com.ga.commentapi.model.commentModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;


public interface CommentService {

    public commentModel createComment(Long postId, commentModel comment, String id, String username) throws JsonProcessingException;
    public HttpStatus deleteComment(long commentId);
    public void deleteCommentByPostId(String message);
    public Iterable<commentModel> getCommentsByPostId(long postId);
    public Iterable<commentModel> getCommentsByUsername(String username);

}
