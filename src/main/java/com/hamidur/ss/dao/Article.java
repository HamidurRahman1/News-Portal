package com.hamidur.ss.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Article
{
    private Integer articleId;
    private String title;
    private String body;
    private LocalDate publishDate;
    private List<Comment> comments;
    private Set<Author> authors;

    public Article() {}

    public Article(Integer articleId, String title, String body, LocalDate publishDate, List<Comment> comments, Set<Author> authors) {
        this.articleId = articleId;
        this.title = title;
        this.body = body;
        this.publishDate = publishDate;
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

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
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

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", publishDate=" + publishDate +
                ", comments=" + comments +
                ", authors=" + authors.size() +
                '}';
    }
}
