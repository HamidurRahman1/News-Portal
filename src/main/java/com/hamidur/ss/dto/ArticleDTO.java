package com.hamidur.ss.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.hamidur.ss.dao.models.Author;
import com.hamidur.ss.dao.models.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO implements Serializable
{
    private Integer articleId;

    @NotNull(message = "title cannot be null")
    @NotBlank(message = "title cannot be empty")
    @Size(min = 1, max = 1000, message = "title can only be in length of 25-1000 characters")
    private String title;

    @NotNull(message = "body cannot be null")
    @NotBlank(message = "body cannot be empty")
    @Size(min = 1, max = 10000, message = "body can only be in length of 1000-10000 characters")
    private String body;

    @NotNull(message = "datetime cannot be null")
    private LocalDateTime dateTime;

    @NotNull(message = "publish property cannot be null")
    private boolean publish;

    private List<Comment> comments;

    private Set<Author> authors;
}
