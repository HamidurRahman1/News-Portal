package com.hamidur.ss.dao;

import java.util.Objects;

public class Comment
{
    private Integer commentId;
    private String comment;
    private Article article;

    public Comment(Integer commentId, String comment, Article article) {
        this.commentId = commentId;
        this.comment = comment;
        this.article = article;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getCommentId(), comment.getCommentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommentId());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", article=" + article.getArticleId() +
                '}';
    }
}
