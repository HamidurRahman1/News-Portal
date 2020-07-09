package com.hamidur.ss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO implements Serializable
{
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer articleId;

    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be empty")
    @Size(min = 1, max = 1000, message = "title can only be in length of 25-1000 characters")
    private String title;

    @NotNull(message = "body cannot be null")
    @NotBlank(message = "body cannot be empty")
    @Size(min = 1, max = 10000, message = "body can only be in length of 1000-10000 characters")
    private String body;

    @NotNull(message = "timestamp cannot be null")
    @NotBlank(message = "body cannot be empty")
    private String timestamp;

    @NotNull(message = "publish property cannot be null")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean publish;

    private List<CommentDTO> comments;

    private Set<AuthorDTO> authors;

    public ArticleDTO() {
    }

    public ArticleDTO(Integer articleId, String title, String body, String datetime, boolean publish, List<CommentDTO> comments, Set<AuthorDTO> authors)
    {
        this.articleId = articleId;
        this.title = title;
        this.body = body;
        this.publish = publish;
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

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public Set<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorDTO> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", publish=" + publish +
                ", comments=" + comments +
                '}';
    }
}
