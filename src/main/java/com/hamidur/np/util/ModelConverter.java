package com.hamidur.np.util;

import com.hamidur.np.auth.models.Role;
import com.hamidur.np.auth.models.User;
import com.hamidur.np.dao.models.Article;
import com.hamidur.np.dao.models.Comment;
import com.hamidur.np.dto.ArticleDTO;
import com.hamidur.np.dto.CommentDTO;
import com.hamidur.np.dto.RoleDTO;
import com.hamidur.np.dto.UserDTO;

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
            articleDTO.setPublished(article1.getPublished());
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
        {
            UserDTO userDTO = new UserDTO(obj.getUserId(), obj.getFirstName(), obj.getLastName(), null, null, null, null);
            userDTO.setEnabled(obj.getEnabled());
            return userDTO;
        }).collect(Collectors.toSet());
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
        return articles.stream().map(obj -> new ArticleDTO(obj.getArticleId(), obj.getTitle(), obj.getBody(), obj.getTimestamp(), obj.getPublished(), null, null)).collect(Collectors.toSet());
    }

    public CommentDTO commentToDTOComment(Comment comment)
    {
        return commentsToDTOComments(Arrays.asList(comment)).get(0);
    }
}
