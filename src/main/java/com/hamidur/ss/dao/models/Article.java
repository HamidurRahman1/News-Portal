package com.hamidur.ss.dao.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
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

    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be empty")
    @Size(min = 1, max = 1000, message = "title can only be in length of 25-1000 characters")
    @Column(name = "title", nullable = false, length = 1000)
    private String title;

    @NotNull(message = "body cannot be null")
    @NotBlank(message = "body cannot be empty")
    @Size(min = 1, max = 10000, message = "body can only be in length of 1000-10000 characters")
    @Column(name = "body", nullable = false, length = 10000)
    private String body;

    @NotNull(message = "datetime cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "datetime", nullable = false)
    private LocalDateTime dateTime;

    @NotNull(message = "publish property cannot be null")
    @Column(name = "is_published", nullable = false, updatable = true)
    private boolean publish;

    @JsonBackReference
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany(mappedBy = "articles")
    private Set<Author> authors;

    public Article() {}

    public Article(Integer articleId, String title, String body, LocalDateTime dateTime, List<Comment> comments, Set<Author> authors) {
        this.articleId = articleId;
        this.title = title;
        this.body = body;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getArticles().add(this);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getArticles().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals(getArticleId(), article.getArticleId()) &&
                Objects.equals(getTitle(), article.getTitle()) &&
                Objects.equals(getBody(), article.getBody()) &&
                Objects.equals(getDateTime(), article.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArticleId(), getTitle(), getBody(), getDateTime());
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", datetime=" + dateTime +
                ", isPublished=" + publish +
                '}';
    }
}
