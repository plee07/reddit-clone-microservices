package com.ga.commentapi.commentapi.service;

import com.ga.commentapi.commentapi.config.JwtUtil;
import com.ga.commentapi.commentapi.model.Comment;
import com.ga.commentapi.commentapi.model.UserBean;
import com.ga.commentapi.commentapi.repository.CommentRepository;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment createComment(long postId, Comment comment, String id, String username) {
        comment.setPostId(postId);
        comment.setUserId(Long.parseLong(id));
        comment.setUsername(username);
        comment.setUser(new UserBean(username));
        return commentRepository.save(comment);
    }

    @Override
    public HttpStatus deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
        return HttpStatus.OK;
    }

    @Override
    public Iterable<Comment> getCommentsByPostId(long postId) {
        Iterable<Comment> commentList = commentRepository.findCommentByPostId(postId);
        for(Comment comment : commentList){
            comment.setUser(new UserBean(comment.getUsername()));
        }
        return commentList;
    }

    @Override
    public List<Comment> getCommentsByUser(String jwToken) {
        return null;
    }

}
