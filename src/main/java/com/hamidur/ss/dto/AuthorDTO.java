package com.hamidur.ss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDTO implements Serializable
{
    private Integer authorId;
    private Set<ArticleDTO> articles;

    public AuthorDTO() {
    }

    public AuthorDTO(Integer authorId, Set<ArticleDTO> articles) {
        this.authorId = authorId;
        this.articles = articles;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Set<ArticleDTO> getArticles() {
        return articles;
    }

    public void setArticles(Set<ArticleDTO> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "authorId=" + authorId +
                ", articles=" + articles +
                '}';
    }
}
