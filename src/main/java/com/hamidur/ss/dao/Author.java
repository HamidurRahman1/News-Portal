package com.hamidur.ss.dao;

import java.util.Set;

public class Author
{
    private Integer authorId;
    private String firstName;
    private String lastName;
    private Set<Article> articles;

    public Author() {}

    public Author(Integer authorId, String firstName, String lastName, Set<Article> articles) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.articles = articles;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", articles=" + articles +
                '}';
    }
}
