package com.hamidur.ss.rest;

import com.hamidur.ss.dao.models.Comment;

import com.hamidur.ss.dao.repos.ArticleRepository;
import com.hamidur.ss.dao.repos.AuthorRepository;
import com.hamidur.ss.dao.repos.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restricted")
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

    @GetMapping(value = "/comment/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer commentId)
    {
        Comment comment = commentRepository.findByCommentId(commentId);
        comment.setArticle(null);
        return new ResponseEntity<>(comment, HttpStatus.NOT_FOUND);
    }
}
