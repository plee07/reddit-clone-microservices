
/**
 * @author Todd Singletary & Phil Lee
 * @version Java version 1.11
 *
 */

package com.ga.commentapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "comments")
public class CommentModel {
    /**
     * This class creates the table for our comment service.
     * Comments are found through {@link com.ga.commentapi.repository.CommentRepository}
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
     * The databases arent't directly related but when we create a comment, the
     * API gateway sends over the username and userId from authentication and we link
     * the user to the comment like this.
     */
    private Long userId;


    @Column
    @JsonIgnore
    /**
     * This is the username of the user who created the comment. Can be found in UserBean.
     */
    private String username;

    @Transient
    /**
     * This is used for email notification. When set true, you will recieve a email when someone writes on your
     * post with this comment. When false, you will not get a email notification.
     */
    private boolean notifyOP;

    @Transient
    /**
     * Object Userbean represents the user who wrote the comment. Contains methods get and set username.
     */
    UserBean user;

    /**
     * Our Default constructor
     */
    public CommentModel(){}

    /**
     * returns the Id of the comment
     * @return Long
     */
    public Long getCommentId() {
        return commentId;
    }

    /**
     * Sets the current comment Id
     * @param commentId Type Long
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    /**
     * returns the Text of the comment
     * @return String
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the comment
     * @param text Type String
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the Id of the post the comment is on
     * @return String
     */
    public Long getPostId() {
        return postId;
    }

    /**
     * Sets the Id of the post
     * @param postId Type Long
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * Returns the Id of the User who wrote the comment
     * @return Long
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the Id of the User who wrote the comment
     * @param userId Type Long
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Returns the username of the User who wrote the comment
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the User who wrote the comment
     * @param username Type String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the User object of the user
     * @return UserBean
     */
    public UserBean getUser() {
        return user;
    }

    /**
     * Sets the User object of the user who wrote the post
     * @param user Type UserBean
     */
    public void setUser(UserBean user) {
        this.user = user;
    }

    /**
     * Return notifyOP for email notification
     * @return boolean
     */
    public boolean isNotifyOP() {
        return notifyOP;
    }

    /**
     * Sets notifyOP for email notification
     * @param notifyOP Type boolean
     */
    public void setNotifyOP(boolean notifyOP) {
        this.notifyOP = notifyOP;
    }
}
