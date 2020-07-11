package com.hamidur.ss.util;

import com.hamidur.ss.auth.models.Role;
import com.hamidur.ss.auth.models.User;
import com.hamidur.ss.dao.models.Article;
import com.hamidur.ss.dao.models.Comment;
import com.hamidur.ss.dto.ArticleDTO;
import com.hamidur.ss.dto.CommentDTO;
import com.hamidur.ss.dto.RoleDTO;
import com.hamidur.ss.dto.UserDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModelConverter
{
    public Set<ArticleDTO> articlesToDTOArticles(Set<Article> articles)
    {
        return articles.stream().map(article1 -> {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setArticleId(article1.getArticleId());
            articleDTO.setTitle(article1.getTitle());
            articleDTO.setBody(article1.getBody());
            articleDTO.setTimestamp(article1.getTimestamp());
            articleDTO.setPublish(article1.getPublish());
            if(!article1.getAuthors().isEmpty())
                articleDTO.setAuthors(article1.getAuthors().stream().map(user -> new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), null, null, null, null)).collect(Collectors.toSet()));
            return articleDTO;
        }).collect(Collectors.toSet());
    }

    public List<CommentDTO> commentsToDTOComments(List<Comment> comments)
    {
        return comments.isEmpty() ? null : comments.stream().map(comment -> new CommentDTO(comment.getCommentId(), comment.getComment())).collect(Collectors.toList());
    }

    public Set<UserDTO> usersToDTOUsers(Set<User> users)
    {
        return users.stream().map(obj ->
                new UserDTO(obj.getUserId(), obj.getFirstName(), obj.getLastName(), null, null, null, null))
                .collect(Collectors.toSet());
    }

    public UserDTO authorToDTOAuthor(User user)
    {
        return new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), null, null, rolesToDTORoles(user.getRoles()), articlesToJustDTOArticles(user.getArticles()));
    }

    public Set<RoleDTO> rolesToDTORoles(Set<Role> roles)
    {
        return roles.stream().map(obj -> new RoleDTO(obj.getRoleId(), obj.getRole())).collect(Collectors.toSet());
    }

    public Set<ArticleDTO> articlesToJustDTOArticles(Set<Article> articles)
    {
        return articles.stream().map(obj -> new ArticleDTO(obj.getArticleId(), obj.getTitle(), obj.getBody(), obj.getTimestamp(), obj.getPublish(), null, null)).collect(Collectors.toSet());
    }

    public CommentDTO commentToDTOComment(Comment comment)
    {
        return commentsToDTOComments(Arrays.asList(comment)).get(0);
    }
}
