package com.hamidur.ss.rest;

import com.hamidur.ss.dto.ArticleDTO;
import com.hamidur.ss.dto.CommentDTO;
import com.hamidur.ss.dto.LoginDTO;
import com.hamidur.ss.dto.UserDTO;
import com.hamidur.ss.auth.models.User;
import com.hamidur.ss.auth.services.AppUserDetails;
import com.hamidur.ss.auth.services.UserService;
import com.hamidur.ss.services.ArticleService;
import com.hamidur.ss.services.CommentService;
import com.hamidur.ss.util.ModelConverter;

import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    private final ModelConverter modelConverter;

    @Autowired
    public PublicRESTController(final ArticleService articleService, final CommentService commentService,
                                final UserService userService, final DaoAuthenticationProvider daoAuthenticationProvider,
                                final ModelMapper modelMapper, final ModelConverter modelConverter) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.modelMapper = modelMapper;
        this.modelConverter = modelConverter;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        Authentication authentication = daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        User user = ((AppUserDetails)authentication.getPrincipal()).getUser();
        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }

    @PostMapping(value = "/user/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> insertUser(@Valid @RequestBody UserDTO userDTO)
    {
        User user = userService.insertUser(modelMapper.map(userDTO, User.class));
        UserDTO userDTO1 = modelMapper.map(user, UserDTO.class);
        userDTO1.setArticles(null);
        return new ResponseEntity<>(userDTO1, HttpStatus.OK);
    }

    @GetMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<ArticleDTO>> getArticles()
    {
        Set<ArticleDTO> articleDTOS = modelConverter.articlesToDTOArticles(articleService.getAllArticles());
        return new ResponseEntity<>(articleDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommentDTO>> getComments()
    {
        return new ResponseEntity<>(modelConverter.commentsToDTOComments(commentService.getAllComments()), HttpStatus.OK);
    }

    @GetMapping(value = "/articles/no-author", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<ArticleDTO>> getArticlesWithNoAuthor()
    {
        Set<ArticleDTO> articleDTOS = modelConverter.articlesToDTOArticles(articleService.getAllArticlesWithNoAuthor());
        return new ResponseEntity<>(articleDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/article/{articleId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommentDTO>> getCommentsByArticleId(@PositiveOrZero @PathVariable Integer articleId)
    {
        return new ResponseEntity<>(modelConverter.commentsToDTOComments(commentService.getAllCommentsByArticleId(articleId)), HttpStatus.OK);
    }
}
