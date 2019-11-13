package com.ga.commentapi.commentapi.service;

import com.ga.commentapi.commentapi.config.JwtUtil;
import com.ga.commentapi.commentapi.model.Comment;
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

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public Comment createComment(long postId, Comment comment, String jwToken) {
        System.out.println(jwtUtil.getUsernameFromToken(jwToken.substring(6)));

        return commentRepository.save(comment);
    }

    @Override
    public HttpStatus deleteComment(long commentId, String jwToken) {
        System.out.println(jwtUtil.getUsernameFromToken(jwToken.substring(6)));
        commentRepository.deleteById(commentId);
        return HttpStatus.OK;
    }

    @Override
    public Iterable<Comment> getCommentsByPostId(long postId) {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getCommentsByUser(String jwToken) {
        return null;
    }

//    @Override
//    public List<Comment> getCommentsByUser(String jwToken) {
//        String username = null;
//        username = jwtUtil.getUsernameFromToken(jwToken);
//        User user = username.userRepository.get();
//        return commentRepository.findAllById(user.getId());
//    }
}
