package com.hamidur.ss.dao.repos;

import com.hamidur.ss.dao.models.Article;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer>
{
    // returns all Articles
    Set<Article> findAll();
}
