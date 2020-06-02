package com.hamidur.ss.dao.repos;

import com.hamidur.ss.dao.models.Author;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer>
{
    // returns all Authors
    Set<Author> findAll();

    // returns a Author associated with the id
    Author findByAuthorId(Integer authorId);
}
