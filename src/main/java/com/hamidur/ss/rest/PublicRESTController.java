package com.hamidur.ss.rest;

import com.hamidur.ss.dto.LoginDTO;
import com.hamidur.ss.auth.models.User;
import com.hamidur.ss.auth.services.AppUserDetails;
import com.hamidur.ss.auth.services.UserService;
import com.hamidur.ss.dao.models.Article;
import com.hamidur.ss.dao.models.Comment;
import com.hamidur.ss.services.ArticleService;
import com.hamidur.ss.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/public")
@Validated
public class PublicRESTController
{
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    public PublicRESTController(final ArticleService articleService, final CommentService commentService,
                                final UserService userService, final DaoAuthenticationProvider daoAuthenticationProvider) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody LoginDTO loginDTO) {
        Authentication authentication = daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        return new ResponseEntity<>(((AppUserDetails)authentication.getPrincipal()).getUser(), HttpStatus.OK);
    }

    @PostMapping(value = "/user/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> insertUser(@Valid @RequestBody User user)
    {
        return new ResponseEntity<>(userService.insertUser(user), HttpStatus.OK);
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

    @GetMapping(value = "/articles/no-author", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Article>> getArticlesWithNoAuthor()
    {
        return new ResponseEntity<>(articleService.getAllArticlesWithNoAuthor(), HttpStatus.OK);
    }

    @GetMapping(value = "/article/{articleId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Comment>> getCommentsByArticleId(@PositiveOrZero @PathVariable Integer articleId)
    {
        return new ResponseEntity<>(commentService.getAllCommentsByArticleId(articleId), HttpStatus.OK);
    }
}
