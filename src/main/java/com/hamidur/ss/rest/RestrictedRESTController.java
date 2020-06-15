package com.hamidur.ss.rest;

import com.hamidur.ss.dao.models.Article;
import com.hamidur.ss.dao.models.Author;
import com.hamidur.ss.dao.models.Comment;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import com.hamidur.ss.services.ArticleService;
import com.hamidur.ss.services.AuthorService;
import com.hamidur.ss.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/r")
@Validated
public class RestrictedRESTController
{
    private final AuthorService authorService;
    private final ArticleService articleService;
    private final CommentService commentService;

    @Autowired
    public RestrictedRESTController(final AuthorService authorService,
                                    final ArticleService articleService,
                                    final CommentService commentService) {
        this.authorService = authorService;
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @GetMapping(value = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Author>> getAuthors()
    {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }

    @GetMapping(value = "/author/{authorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> getAuthor(@PositiveOrZero @PathVariable Integer authorId)
    {
        return new ResponseEntity<>(authorService.getAuthorById(authorId), HttpStatus.OK);
    }

    @GetMapping(value = "/author/{authorId}/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Article>> getArticlesByAuthorId(@PositiveOrZero @PathVariable Integer authorId)
    {
        return new ResponseEntity<>(articleService.getArticlesByAuthorId(authorId), HttpStatus.OK);
    }

    @GetMapping(value = "/comment/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> getCommentById(@PositiveOrZero @PathVariable Integer commentId)
    {
        try
        {
            Comment comment = commentService.getCommentById(commentId);
            comment.setArticle(null);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
        catch (NotFoundException ex)
        {
            throw new NotFoundException(ex.getErrorMessage(), HttpStatus.NOT_FOUND.value());
        }
    }

    @DeleteMapping(value = "/delete/author/{authorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAuthorById(@PositiveOrZero @PathVariable Integer authorId)
    {
        if(authorService.deleteAuthorById(authorId))
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new NotFoundException("No author found with id="+authorId+" to delete");
    }

    @DeleteMapping(value = "/delete/comment/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PositiveOrZero @PathVariable Integer commentId)
    {
        if(commentService.deleteCommentById(commentId))
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new NotFoundException("No comment found with id="+commentId+" to delete");
    }

    @DeleteMapping(value = "/delete/article/{articleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteArticleById(@PositiveOrZero @PathVariable Integer articleId)
    {
        if(articleService.deleteArticleById(articleId))
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new NotFoundException("No article found with id="+articleId+" to delete");
    }

    @PostMapping(value = "/insert/author", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> insertAuthor(@Valid @RequestBody Author author)
    {
        return new ResponseEntity<>(authorService.insertAuthor(author), HttpStatus.CREATED);
    }

    @PostMapping(value = "/insert/article", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> insertArticle(@Valid @RequestBody Article article)
    {
        return new ResponseEntity<>(articleService.insertArticle(article), HttpStatus.CREATED);
    }

    @PostMapping(value = "/insert/comment/article", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> insertCommentByArticleId(@Valid @RequestBody Comment comment)
    {
        if(commentService.insertComment(comment))
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update/author", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> updateAuthor(@Valid @RequestBody Author author)
    {
        return new ResponseEntity<>(authorService.updateAuthor(author), HttpStatus.OK);
    }

    @PutMapping(value = "/update/article", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> updateArticle(@Valid @RequestBody Article article)
    {
        return new ResponseEntity<>(articleService.updateArticle(article), HttpStatus.OK);
    }

    @PutMapping(value = "/update/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCommentById(@Valid @RequestBody Comment comment)
    {
        if(commentService.updateCommentByCommentIdAndArticleId(comment))
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new NotFoundException("No comment found with id="+comment.getCommentId()+" to update");
    }
}
