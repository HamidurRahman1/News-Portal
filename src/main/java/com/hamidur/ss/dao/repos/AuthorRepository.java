package com.hamidur.ss.dao.repos;

import com.hamidur.ss.dao.models.Author;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer>
{
    // returns all Authors
    Set<Author> findAll();

    // returns a Author associated with the id
    Author findByAuthorId(Integer authorId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "delete from authors_articles aa where aa.author_id = (:authorId);" +
                    "delete from authors a where a.author_id = (:authorId);"+
                    "delete from users_roles where user_id = :id;" +
                    "delete from users where user_id = :id")
    int deleteAuthorById(@Param("authorId") Integer authorId, @Param("id") Integer userId);
}
