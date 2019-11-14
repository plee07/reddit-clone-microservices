package com.ga.commentapi.commentapi.repository;

import com.ga.commentapi.commentapi.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    public Iterable<Comment> findCommentByPostId(Long postId);

    @Transactional
    public Iterable<Comment> deleteCommentsByPostId(Long postId);

}
