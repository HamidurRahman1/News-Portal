package com.hamidur.ss.rest;

import com.hamidur.ss.dao.repos.ArticleRepository;
import com.hamidur.ss.dao.repos.AuthorRepository;
import com.hamidur.ss.dao.repos.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restricted")
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


}
