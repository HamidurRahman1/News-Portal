package com.hamidur.ss.rest;

import com.hamidur.ss.dao.models.Article;
import com.hamidur.ss.dao.models.Author;
import com.hamidur.ss.dao.models.Comment;

import com.hamidur.ss.dao.repos.ArticleRepository;
import com.hamidur.ss.dao.repos.AuthorRepository;
import com.hamidur.ss.dao.repos.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/r")
public class RestrictedRESTController
{
    private final AuthorRepository authorRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public RestrictedRESTController(final AuthorRepository authorRepository,
                                    final ArticleRepository articleRepository,
                                    final CommentRepository commentRepository) {
        this.authorRepository = authorRepository;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @PostMapping(value = "/insert/author", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> insertAuthor(@RequestBody Author author)
    {
        Author newAuthor = authorRepository.save(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.OK);
    }

    @PostMapping(value = "/insert/article", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> insertArticle(@RequestBody Article article)
    {
        Author retrievedAuthor = authorRepository.findByAuthorId(article.getAuthors().iterator().next().getAuthorId());
        retrievedAuthor.getArticles().add(article);
        article.getAuthors().add(retrievedAuthor);
        return new ResponseEntity<>(new Article(), HttpStatus.OK);
    }

    @GetMapping(value = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Author>> getAuthors()
    {
        Set<Author> authors = authorRepository.findAll();
        for(Author author: authors) author.setArticles(null);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping(value = "/author/{authorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> getAuthor(@Size(min = 1) @PathVariable Integer authorId)
    {
        Author author = authorRepository.findByAuthorId(authorId);
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
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer commentId)
    {
        Comment comment = commentRepository.findByCommentId(commentId);
        comment.setArticle(null);
        return new ResponseEntity<>(comment, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/access-denied", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> accessDenied(HttpServletRequest request, HttpServletResponse response)
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
