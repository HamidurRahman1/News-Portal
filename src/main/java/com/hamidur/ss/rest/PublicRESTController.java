package com.hamidur.ss.rest;

import com.hamidur.ss.dao.models.Article;
import com.hamidur.ss.dao.models.Comment;

import com.hamidur.ss.dao.repos.ArticleRepository;
import com.hamidur.ss.dao.repos.AuthorRepository;

import com.hamidur.ss.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/public")
@Validated
public class PublicRESTController
{
    private final AuthorRepository authorRepository;
    private final ArticleRepository articleRepository;
    private final CommentService commentService;

    @Autowired
    public PublicRESTController(AuthorRepository authorRepository,
                                ArticleRepository articleRepository,
                                CommentService commentService) {
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
        this.commentService = commentService;
    }

    @GetMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Article>> getArticles()
    {
        Set<Article> articles = articleRepository.findAll();
        for(Article article: articles)
        {
            article.setAuthors(null);
            article.setComments(null);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getComments()
    {
        List<Comment> comments = commentService.getAllComments();
        comments.forEach(comment -> comment.setArticle(null));
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping(value = "/article/{articleId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getCommentsByArticleId(@PositiveOrZero @PathVariable Integer articleId)
    {
        List<Comment> comments = commentService.getAllCommentsByArticleId(articleId);
        comments.forEach(comment -> comment.setArticle(null));
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
