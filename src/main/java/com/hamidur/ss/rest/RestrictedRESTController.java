package com.hamidur.ss.rest;

import com.hamidur.ss.dao.models.Article;
import com.hamidur.ss.dao.models.Author;
import com.hamidur.ss.dao.models.Comment;

import com.hamidur.ss.dao.repos.ArticleRepository;

import com.hamidur.ss.exceptions.custom.NotFoundException;
import com.hamidur.ss.services.AuthorService;
import com.hamidur.ss.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/r")
@Validated
public class RestrictedRESTController
{
    private final AuthorService authorService;
    private final ArticleRepository articleRepository;
    private final CommentService commentService;

    @Autowired
    public RestrictedRESTController(final AuthorService authorService,
                                    final ArticleRepository articleRepository,
                                    final CommentService commentService) {
        this.authorService = authorService;
        this.articleRepository = articleRepository;
        this.commentService = commentService;
    }

    @GetMapping(value = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Author>> getAuthors()
    {
        Set<Author> authors = authorService.getAllAuthors();
        for(Author author: authors) author.setArticles(null);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping(value = "/author/{authorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> getAuthor(@PositiveOrZero @PathVariable Integer authorId)
    {
        Author author = authorService.getAuthorById(authorId);
        author.setArticles(null);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping(value = "/author/{authorId}/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Article>> getArticlesByAuthorId(@Size(min = 1) @PathVariable Integer authorId)
    {
        Set<Article> articles = articleRepository.getArticlesByAuthorId(authorId);
        articles.forEach(article ->
        {
            article.setAuthors(null);
            article.setComments(null);
        });
        return new ResponseEntity<>(articles, HttpStatus.OK);
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
        if(commentService.deleteCommentById(authorId))
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
    public ResponseEntity<Void> deleteArticleById(@Size(min = 1) @PathVariable Integer articleId)
    {
        articleRepository.deleteById(articleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/insert/author", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> insertAuthor(@Valid @RequestBody Author author)
    {
        return new ResponseEntity<>(authorService.insertAuthor(author), HttpStatus.OK);
    }

    @PostMapping(value = "/insert/article", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> insertArticle(@Valid @RequestBody Article article)
    {
//        Article retrievedArticle = articleRepository.save(article);
//
//        Set<Integer> authorIds = new HashSet<>();
//        article.getAuthors().forEach(author -> authorIds.add(author.getAuthorId()));
//
//        Iterable<Author> iterables = authorRepository.findAllById(authorIds);
//
//        iterables.forEach(author ->
//        {
//            author.getArticles().add(retrievedArticle);
//            retrievedArticle.getAuthors().add(author);
//        });
//
//        authorRepository.saveAll(iterables);
//
//        Article response = new Article();
//        response.setArticleId(retrievedArticle.getArticleId());
//        response.setTitle(retrievedArticle.getTitle());
//        response.setBody(retrievedArticle.getBody());
//        response.setPublishDate(retrievedArticle.getPublishDate());

        return new ResponseEntity<>(new Article(), HttpStatus.OK);
    }

    @PostMapping(value = "/insert/comment/article/{articleId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> insertCommentByArticleId(@PositiveOrZero @PathVariable Integer articleId,
                                                         @Valid @RequestBody Comment comment)
    {
        if(commentService.insertComment(articleId, comment))
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update/author", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> updateAuthor(@Valid @RequestBody Author author)
    {
        Author author1 = authorService.updateAuthor(author);
        if(author1 == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(author1, HttpStatus.OK);
    }

    @PutMapping(value = "/update/article/{articleId}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> updateArticle(@Size(min = 1) @PathVariable Integer articleId, @Valid @RequestBody Article article)
    {
        Optional<Article> optional = articleRepository.findById(articleId);
        if(optional.isPresent())
        {
            if(optional.get().getArticleId().equals(article.getArticleId()) && articleId.equals(optional.get().getArticleId()))
            {
                Article article1 = optional.get();
                article1.setTitle(article.getTitle());
                article1.setBody(article.getBody());
                article1.setPublishDate(article.getPublishDate());
                articleRepository.save(article1);

                return new ResponseEntity<>(article, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new Article(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCommentById(@Valid @RequestBody Comment comment)
    {
        if(commentService.updateCommentByCommentIdAndArticleId(comment))
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new NotFoundException("No comment found with id="+comment.getCommentId()+" to update");
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
