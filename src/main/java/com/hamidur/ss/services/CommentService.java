package com.hamidur.ss.services;

import com.hamidur.ss.dao.models.Comment;
import com.hamidur.ss.dao.repos.CommentRepository;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Comment> getAllComments() throws NotFoundException
    {
        List<Comment> comments = commentRepository.findAll();
        if(comments == null)
        {
            throw new NotFoundException("No comments found to return");
        }
        return comments;
    }
}
