package com.example.studentagency.bean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2020/01/06
 * desc:
 */
public class CommentBean {
    private int commentId;
    private int userId;
    private int indentId;
    private String content;
    private String commentTime;
    private String avatar;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIndentId() {
        return indentId;
    }

    public void setIndentId(int indentId) {
        this.indentId = indentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }
}
