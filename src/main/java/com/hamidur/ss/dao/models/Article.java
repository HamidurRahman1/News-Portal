package com.hamidur.ss.dao.models;

import com.hamidur.ss.auth.models.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", nullable = false, updatable = false, unique = true)
    private Integer articleId;

    @Column(name = "title", nullable = false, length = 1000)
    private String title;

    @Column(name = "body", nullable = false, length = 10000)
    private String body;

    @Column(name = "timestamp", nullable = false, length = 50)
    private String timestamp;

    @Column(name = "is_published", nullable = false, updatable = true)
    private boolean publish;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany(mappedBy = "articles")
    private Set<User> authors;

    public Article() {}

    public Article(Integer articleId, String title, String body, String timestamp, List<Comment> comments, Set<User> authors) {
        this.articleId = articleId;
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
        this.comments = comments;
        this.authors = authors;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<User> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<User> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(getArticleId(), article.getArticleId()) &&
                Objects.equals(getTitle(), article.getTitle()) &&
                Objects.equals(getBody(), article.getBody()) &&
                Objects.equals(getTimestamp(), article.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArticleId(), getTitle(), getBody(), getTimestamp());
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", timestamp=" + timestamp +
                ", isPublished=" + publish +
                '}';
    }
}
