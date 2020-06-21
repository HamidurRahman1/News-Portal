package com.hamidur.ss.rest;

import com.hamidur.ss.dao.models.Article;
import com.hamidur.ss.dao.models.Comment;
import com.hamidur.ss.services.ArticleService;
import com.hamidur.ss.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ArticleService articleService;
    private final CommentService commentService;

    @Autowired
    public PublicRESTController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @GetMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Article>> getArticles()
    {
        return new ResponseEntity<>(articleService.getAllArticles(), HttpStatus.OK);
    }

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getComments()
    {
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }

    @GetMapping(value = "/article/{articleId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getCommentsByArticleId(@PositiveOrZero @PathVariable Integer articleId)
    {
        return new ResponseEntity<>(commentService.getAllCommentsByArticleId(articleId), HttpStatus.OK);
    }

    @GetMapping(value = "/access-denied", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> accessDenied()
    {
        StringBuilder stringBuilder = new StringBuilder();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null)
        {
            stringBuilder
                    .append("User: ").append(authentication.getName())
                    .append(" has tried to access protected resources without proper credentials.")
                    .append("\n")
                    .append("ResponseStatus: ").append(HttpStatus.FORBIDDEN);
            return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.FORBIDDEN);
        }
        stringBuilder
                .append("Request URI is protected, requires proper credentials for access.")
                .append("\n")
                .append("ResponseStatus: ").append(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.FORBIDDEN);
    }
}
