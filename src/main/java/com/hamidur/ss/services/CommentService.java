package com.hamidur.ss.services;

import com.hamidur.ss.dao.models.Comment;
import com.hamidur.ss.dao.repos.CommentRepository;
import com.hamidur.ss.exceptions.custom.ConstraintViolationException;
import com.hamidur.ss.exceptions.custom.MissingAttribute;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService
{
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public boolean insertComment(Comment comment) throws MissingAttribute, ConstraintViolationException
    {
        try
        {
            if(comment.getArticle().getArticleId() == null)
                throw new MissingAttribute("Comment must be associated with an article");
            return commentRepository.insertComment(comment.getArticle().getArticleId(), comment.getComment()) >= 1;
        }
        catch (DataIntegrityViolationException ex)
        {
            throw new ConstraintViolationException("No article found with id="+comment.getArticle().getArticleId()+" to insert comment");
        }
    }

    public List<Comment> getAllComments() throws NotFoundException
    {
        List<Comment> comments = commentRepository.findAll();
        if(comments == null || comments.isEmpty())
            throw new NotFoundException("No comments found to return");
        return comments;
    }

    public Comment getCommentById(Integer commentId) throws NotFoundException
    {
        Comment comment = commentRepository.findByCommentId(commentId);
        if(comment == null)
            throw new NotFoundException("No comment found associated with commentId="+commentId);
        return comment;
    }

    public List<Comment> getAllCommentsByArticleId(Integer articleId) throws NotFoundException
    {
        List<Comment> comments = commentRepository.getCommentsByArticleId(articleId);
        if(comments == null || comments.isEmpty())
            throw new NotFoundException("No comments found associated with articleId="+articleId);
        return comments;
    }

    public boolean updateCommentByCommentId(Comment comment) throws MissingAttribute
    {
        if(comment.getCommentId() == null)
            throw new MissingAttribute("Comment id must be present to update a comment");
        return commentRepository.updateCommentByCommentId(comment.getCommentId(), comment.getComment()) >= 1;
    }

    public boolean deleteCommentById(Integer commentId)
    {
        return commentRepository.deleteByCommentId(commentId) >= 1;
    }
}
