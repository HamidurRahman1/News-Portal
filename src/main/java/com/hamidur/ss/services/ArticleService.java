package com.hamidur.ss.services;

import com.hamidur.ss.dao.models.Article;
import com.hamidur.ss.dao.repos.ArticleRepository;
import com.hamidur.ss.exceptions.custom.MissingAttribute;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class ArticleService
{
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Set<Article> getArticlesBySubString(String text)
    {
        Set<Article> articles = articleRepository.getArticlesByBodyContains(text);
        if(articles == null || articles.isEmpty())
            throw new NotFoundException("No articles found to return");
        return articles;
    }

    @Transactional
    public Article insertArticle(Article article) throws MissingAttribute
    {
        if(article.getAuthors() == null)
            throw new MissingAttribute("At least 1 author must be associated with article. found="+null);

        return null;
    }

    public Set<Article> getAllArticles() throws NotFoundException
    {
        Set<Article> articles = articleRepository.findAll();
        if(articles == null || articles.isEmpty())
            throw new NotFoundException("No articles found to return");
        return articles;
    }

    public Set<Article> getArticlesByAuthorId(Integer authorId) throws NotFoundException
    {
        Set<Article> articles = articleRepository.getArticlesByAuthorId(authorId);
        if(articles == null || articles.isEmpty())
            throw new NotFoundException("No articles found to return for author with id="+authorId);
        return articles;
    }

    public boolean deletePublishedArticleById(Integer articleId)
    {
        boolean existsById = articleRepository.existsById(articleId);
        if(!existsById) return false;
        articleRepository.deletePublishedArticleById(articleId);
        return !articleRepository.existsById(articleId);
    }

    public boolean deleteUnpublishedArticleById(Integer articleId)
    {
        boolean existsById = articleRepository.existsById(articleId);
        if(!existsById) return false;
        articleRepository.deleteUnPublishedArticleById(articleId);
        return !articleRepository.existsById(articleId);
    }

    public Article updateArticle(Article article) throws MissingAttribute, NotFoundException
    {
        if(article.getArticleId() == null)
            throw new MissingAttribute("article must contains its id to be updated");

        Optional<Article> optional = articleRepository.findById(article.getArticleId());

        if(!optional.isPresent())
            throw new NotFoundException("Not article found with associated id="+article.getArticleId());
        else {
            Article article1 = optional.get();
            article1.setTitle(article.getTitle());
            article1.setBody(article.getBody());
            article1.setTimestamp(article.getTimestamp());
            article1.setPublish(article.getPublish());
            return articleRepository.save(article1);
        }
    }

    public Set<Article> getAllArticlesWithNoAuthor() throws NotFoundException
    {
        Set<Article> articles = articleRepository.getPublishedArticlesWithNoAuthor();
        if(articles == null || articles.isEmpty())
            throw new NotFoundException("No articles exists without author to return");
        return articles;
    }
}
