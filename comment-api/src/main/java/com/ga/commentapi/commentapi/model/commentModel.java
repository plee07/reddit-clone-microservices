/**
 * @author Todd Singletary & Phil Lee
 * @version Java version 1.11
 *
 */

package com.ga.commentapi.commentapi.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "comments")
public class commentModel {
    /**
     * This class creates the table for our comment service.
     * Comments are found through {@link com.ga.commentapi.commentapi.repository.CommentRepository}
     *
     */
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * Creates the column "comment_id" in our comments table with type Long.
     */
    private Long commentId;

    @Column
    @NotBlank(message = "Text must be provided")
    @Size(min = 1 , message = "comments can't be null")
    /**
     * Creates the column "text" in our comments table with type String.
     */
    private String text;

    @Column(nullable=false)
    /**
     * Creates the foriegn key "postId" in our comments table with type Long.
     * The databases aren't directly related but when we create a comment,
     * we pass along the postId of the post we are creating a comment on.
     */
    private Long postId;

    @Column(nullable=false)
    /**
     * Creates the foriegn key "userId" in our comments table with type Long.
     * When we create a comment, the userId passed from the Api-Gateway
     * will let us know which user created which comment.
     */
    private Long userId;

    @Column
    @JsonIgnore
    private String username;

    @Transient
    UserBean user;

    /**
     * Our Default constructor
     */
    public commentModel(){}

    /**
     * All of our getters and setters
     * @return These are where we create our data
     */
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
