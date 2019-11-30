package com.ga.commentapi.repository;


import com.ga.commentapi.model.CommentModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends CrudRepository<CommentModel, Long> {

    public Iterable<CommentModel> findCommentByPostId(Long postId);
    public Iterable<CommentModel> findCommentsByUsername(String username);

    @Transactional
    @Modifying
    public Iterable<CommentModel> deleteCommentsByPostId(Long postId);

}
