package com.hamidur.ss.util;

import com.hamidur.ss.dao.models.Article;
import com.hamidur.ss.dao.models.Comment;
import com.hamidur.ss.dto.ArticleDTO;
import com.hamidur.ss.dto.CommentDTO;
import com.hamidur.ss.dto.UserDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModelConverter
{
    private final ModelMapper modelMapper;

    public ModelConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Set<ArticleDTO> articlesToDTOArticles(Set<Article> articles)
    {
        return articles.stream().map(article1 -> {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setArticleId(article1.getArticleId());
            articleDTO.setTitle(article1.getTitle());
            articleDTO.setBody(article1.getBody());
            articleDTO.setTimestamp(article1.getTimestamp());
            articleDTO.setPublish(article1.getPublish());
            articleDTO.setComments(commentsToDTOComments(article1.getComments()));
            if(!article1.getAuthors().isEmpty())
                articleDTO.setAuthors(article1.getAuthors().stream().map(user -> new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), null, null)).collect(Collectors.toSet()));
            return articleDTO;
        }).collect(Collectors.toSet());
    }

    public List<CommentDTO> commentsToDTOComments(List<Comment> comments)
    {
        return comments.isEmpty() ? null : comments.stream().map(comment -> new CommentDTO(comment.getCommentId(), comment.getComment())).collect(Collectors.toList());
    }
}
