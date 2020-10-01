package com.hamidur.np.dao.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, updatable = false, unique = true)
    private Integer commentId;

    @Column(name = "comment", nullable = false, length = 250)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    public Comment() {}

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
        Comment comment1 = (Comment) o;
        return Objects.equals(getCommentId(), comment1.getCommentId()) &&
                Objects.equals(getComment(), comment1.getComment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommentId(), getComment());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
