package com.ga.commentapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ga.commentapi.exception.PostNotFoundException;
import com.ga.commentapi.model.CommentModel;
import org.springframework.http.HttpStatus;


public interface CommentService {

    public CommentModel createComment(Long postId, CommentModel comment, String id, String username)
            throws PostNotFoundException, JsonProcessingException;
    public HttpStatus deleteComment(long commentId);
    public void deleteCommentByPostId(String message);
    public Iterable<CommentModel> getCommentsByPostId(long postId);
    public Iterable<CommentModel> getCommentsByUsername(String username);

}
