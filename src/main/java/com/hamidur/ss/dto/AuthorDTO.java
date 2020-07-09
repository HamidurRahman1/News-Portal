package com.hamidur.ss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDTO implements Serializable
{
    private Set<ArticleDTO> articles;

    public AuthorDTO() {
    }

    public AuthorDTO(Set<ArticleDTO> articles) {
        this.articles = articles;
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
                "articles=" + articles +
                '}';
    }
}
