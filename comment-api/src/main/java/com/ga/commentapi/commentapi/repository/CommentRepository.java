package com.ga.commentapi.commentapi.repository;

import com.ga.commentapi.commentapi.model.commentModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource
public interface CommentRepository extends CrudRepository<commentModel, Long> {

    @RestResource(path = "comment")
    @ApiOperation("Find comments by the postId")
    public Iterable<commentModel> findCommentByPostId(@Param("postId") Long postId);

    @ApiOperation("Find comments by User's username")
    public Iterable<commentModel> findCommentsByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @ApiOperation("Delete comments from Post. Interservice call")
    public Iterable<commentModel> deleteCommentsByPostId(@Param("postId") Long postId);

}
