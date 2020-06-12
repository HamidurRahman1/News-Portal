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
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/r")
@Validated
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
    public ResponseEntity<Comment> getCommentById(@Size(min = 1) @PathVariable Integer commentId)
    {
        Comment comment = commentRepository.findByCommentId(commentId);
        comment.setArticle(null);
        return new ResponseEntity<>(comment, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/delete/author/{authorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAuthorById(@Size(min = 1) @PathVariable Integer authorId)
    {
        authorRepository.deleteById(authorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/comment/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteCommentById(@Size(min = 1) @PathVariable Integer commentId)
    {
        commentRepository.deleteById(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
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
        Author newAuthor = authorRepository.save(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.OK);
    }

    @PostMapping(value = "/insert/article", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Article> insertArticle(@Valid @RequestBody Article article)
    {
        Article retrievedArticle = articleRepository.save(article);

        Set<Integer> authorIds = new HashSet<>();
        article.getAuthors().forEach(author -> authorIds.add(author.getAuthorId()));

        Iterable<Author> iterables = authorRepository.findAllById(authorIds);

        iterables.forEach(author ->
        {
            author.getArticles().add(retrievedArticle);
            retrievedArticle.getAuthors().add(author);
        });

        authorRepository.saveAll(iterables);

        Article response = new Article();
        response.setArticleId(retrievedArticle.getArticleId());
        response.setTitle(retrievedArticle.getTitle());
        response.setBody(retrievedArticle.getBody());
        response.setPublishDate(retrievedArticle.getPublishDate());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/insert/article/{articleId}/comment",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> insertCommentByArticleId(@Size(min = 1) @PathVariable Integer articleId,
                                                            @Valid @RequestBody Comment comment)
    {
        Optional<Article> article = articleRepository.findById(articleId);
        if(article.isPresent())
        {
            article.get().getComments().add(comment);
            comment.setArticle(article.get());
            articleRepository.save(article.get());
            return new ResponseEntity<>(new Comment(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Comment(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/author",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Author> updateAuthor(@Valid @RequestBody Author author)
    {
        if(author.getAuthorId() != null)
        {
            Author author1 = authorRepository.findByAuthorId(author.getAuthorId());
            author1.setFirstName(author.getFirstName());
            author1.setLastName(author.getLastName());
            authorRepository.save(author1);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    @PutMapping(value = "/update/article/{articleId}/comment/{commentId}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> updateCommentById(@Size(min = 1) @PathVariable Integer articleId,
                                                     @Size(min = 1) @PathVariable Integer commentId,
                                                     @Valid @RequestBody Comment comment)
    {
        Optional<Article> optional = articleRepository.findById(articleId);
        if(optional.isPresent())
        {
            if(optional.get().getArticleId().equals(articleId))
            {
                Article article = optional.get();
                for (int i = 0; i < article.getComments().size(); i++)
                {
                    if(comment.getCommentId().equals(article.getComments().get(i).getCommentId())
                            && comment.getCommentId().equals(commentId)) {
                        commentRepository.updateCommentByCommentIdAndArticleId(comment.getComment(), commentId, articleId);
                        break;
                    }
                }
                Comment comment1 = new Comment(commentId, comment.getComment(), null);
                return new ResponseEntity<>(comment1, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new Comment(), HttpStatus.OK);
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
