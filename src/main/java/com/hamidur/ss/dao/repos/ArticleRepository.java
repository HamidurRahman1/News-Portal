package com.hamidur.ss.dao.repos;

import com.hamidur.ss.dao.models.Article;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer>
{
    // returns all Articles
    Set<Article> findAll();

    // returns all Articles written by an author associated with this id
    @Query(nativeQuery = true,
            value = "select * from articles a inner join authors_articles aa " +
                    "on a.article_id = aa.article_id and aa.author_id = (:author_id)")
    Set<Article> getArticlesByAuthorId(@Param("author_id") Integer authorId);
}
