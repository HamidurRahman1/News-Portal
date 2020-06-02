package com.hamidur.ss.dao.repos;

import com.hamidur.ss.dao.models.Comment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer>
{
    // returns All Comments
    List<Comment> findAll();
}
