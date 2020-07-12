package com.hamidur.ss.rest;

import com.hamidur.ss.auth.models.User;
import com.hamidur.ss.auth.services.UserService;
import com.hamidur.ss.dao.models.Article;
import com.hamidur.ss.dao.models.Comment;
import com.hamidur.ss.dto.ArticleDTO;
import com.hamidur.ss.dto.CommentDTO;
import com.hamidur.ss.dto.UserDTO;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import com.hamidur.ss.services.ArticleService;
import com.hamidur.ss.services.CommentService;
import com.hamidur.ss.util.ModelConverter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/r")
@Validated
public class RestrictedRESTController
{
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;
    private final ModelConverter modelConverter;
    private final ModelMapper modelMapper;

    @Autowired
    public RestrictedRESTController(final ArticleService articleService, final CommentService commentService,
                                    final UserService userService, final ModelConverter modelConverter, final ModelMapper modelMapper) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.userService = userService;
        this.modelConverter = modelConverter;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<UserDTO>> getAuthors()
    {
        Set<UserDTO> authors = modelConverter.usersToDTOUsers(userService.getAllAuthors());
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping(value = "/articles/text", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<ArticleDTO>> searchArticle(@RequestParam("bodyContains") String subString)
    {
        Set<ArticleDTO> articleDTOS = modelConverter.articlesToDTOArticles(articleService.getArticlesBySubString(subString));
        return new ResponseEntity<>(articleDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/author/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getAuthor(@PositiveOrZero @PathVariable Integer userId)
    {
        User author = userService.getAuthorByUserId(userId);
        UserDTO dtoAuthor = modelConverter.authorToDTOAuthor(author);
        return new ResponseEntity<>(dtoAuthor, HttpStatus.OK);
    }

    @GetMapping(value = "/author/{userId}/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<ArticleDTO>> getArticlesByAuthorId(@PositiveOrZero @PathVariable Integer userId)
    {
        return new ResponseEntity<>(modelConverter.articlesToJustDTOArticles(articleService.getArticlesByAuthorId(userId)), HttpStatus.OK);
    }

    @GetMapping(value = "/comment/{commentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDTO> getCommentById(@PositiveOrZero @PathVariable Integer commentId)
    {
        return new ResponseEntity<>(modelConverter.commentToDTOComment(commentService.getCommentById(commentId)), HttpStatus.OK);
    }

    @PostMapping(value = "/insert/user/{userId}/role/{roleId}")
    public ResponseEntity<Void> addRole(@PositiveOrZero @PathVariable Integer userId, @PositiveOrZero @PathVariable Integer roleId)
    {
        if(userService.addRole(userId, roleId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/insert/article", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDTO> insertArticle(@Valid @RequestBody ArticleDTO articleDTO)
    {
        Article article = modelMapper.map(articleDTO, Article.class);
        ArticleDTO articleDTO1 = modelMapper.map(articleService.insertArticle(article), ArticleDTO.class);
        return new ResponseEntity<>(articleDTO1, HttpStatus.CREATED);
    }

    @PostMapping(value = "/insert/comment/article/{articleId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> insertCommentByArticleId(@PositiveOrZero @PathVariable Integer articleId, @Valid @RequestBody Comment comment)
    {
        if(commentService.insertComment(articleId, comment))
            return new ResponseEntity<>(HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO)
    {
        User user = modelMapper.map(userDTO, User.class);
        User updatedUser = userService.updateUser(user);
        UserDTO response = modelMapper.map(updatedUser, UserDTO.class);
        response.setArticles(null);
        response.setRoles(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/update/article", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleDTO> updateArticle(@Valid @RequestBody ArticleDTO articleDTO)
    {
        Article article = modelMapper.map(articleDTO, Article.class);
        Article updatedArticle = articleService.updateArticle(article);
        updatedArticle.setAuthors(null);
        updatedArticle.setComments(null);
        ArticleDTO updatedArticleDto = modelMapper.map(updatedArticle, ArticleDTO.class);
        return new ResponseEntity<>(updatedArticleDto, HttpStatus.OK);
    }

    @PutMapping(value = "/update/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCommentById(@Valid @RequestBody CommentDTO commentDTO)
    {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        if(commentService.updateCommentByCommentId(comment))
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new NotFoundException("No comment found with id="+commentDTO.getCommentId()+" to update");
    }

    @PatchMapping(value = "/deactivate/user/{userId}")
    public ResponseEntity<Void> deactivateUserAccount(@PositiveOrZero @PathVariable Integer userId)
    {
        if(userService.deactivateUserAccount(userId))
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/delete/author/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAuthorById(@PositiveOrZero @PathVariable Integer userId)
    {
        if(userService.removeAuthorRole(userId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/delete/comment/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PositiveOrZero @PathVariable Integer commentId)
    {
        if(commentService.deleteCommentById(commentId))
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new NotFoundException("No comment found with id="+commentId+" to delete");
    }

    @DeleteMapping(value = "/delete/published/article/{articleId}")
    public ResponseEntity<Void> deletePublishedArticleById(@PositiveOrZero @PathVariable Integer articleId)
    {
        if(articleService.deletePublishedArticleById(articleId))
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new NotFoundException("No published article found with id="+articleId+" to delete");
    }

    @DeleteMapping(value = "/delete/unpublished/article/{articleId}")
    public ResponseEntity<Void> deleteUnPublishedArticleById(@PositiveOrZero @PathVariable Integer articleId)
    {
        if(articleService.deleteUnpublishedArticleById(articleId))
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new NotFoundException("No unpublished article found with id="+articleId+" to delete");
    }

    @DeleteMapping(value = "/delete/user/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable @PositiveOrZero Integer userId)
    {
        if(userService.deleteUserById(userId))
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new NotFoundException("No user found with user_id="+userId+" to delete");
    }

    @DeleteMapping(value = "/delete/user/{userId}/role/{roleId}")
    public ResponseEntity<Void> deleteRole(@PositiveOrZero @PathVariable Integer userId, @PositiveOrZero @PathVariable Integer roleId)
    {
        if(userService.revokeRole(userId, roleId))
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new NotFoundException("user with userId="+userId+" or role with roleId="+roleId+" not found to delete");
    }
}
