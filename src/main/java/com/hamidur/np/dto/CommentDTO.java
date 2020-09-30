package com.hamidur.np.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO implements Serializable
{
    private Integer commentId;

    @NotNull(message = "comment cannot be null")
    @NotBlank(message = "comment cannot be empty")
    @Size(min = 1, max = 250, message = "comment can only be in length of 1-250 characters")
    private String comment;

    public CommentDTO() {
    }

    public CommentDTO(Integer commentId, String comment) {
        this.commentId = commentId;
        this.comment = comment;
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

    @Override
    public String toString() {
        return "CommentDTO{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                '}';
    }
}
