package com.ga.commentapi.commentapi.repository;

import com.ga.commentapi.commentapi.model.commentModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends CrudRepository<commentModel, Long> {

    public Iterable<commentModel> findCommentByPostId(Long postId);
    public Iterable<commentModel> findCommentByUserId(Long userId);
    public Iterable<commentModel> findCommentsByUsername(String username);

    @Transactional
    @Modifying
    public Iterable<commentModel> deleteCommentsByPostId(Long postId);

}
