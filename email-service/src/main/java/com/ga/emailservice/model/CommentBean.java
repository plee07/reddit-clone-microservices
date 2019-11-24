package com.ga.emailservice.model;

public class CommentBean {

    private Long commentId;
    private String text;
    private Long postId;
    private Long userId;
    private boolean notifyOP;
    private User user;

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

    public boolean isNotifyOP() {
        return notifyOP;
    }

    public void setNotifyOP(boolean notifyOP) {
        this.notifyOP = notifyOP;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
