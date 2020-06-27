package com.hamidur.ss.services;

import com.hamidur.ss.dao.models.Article;
import com.hamidur.ss.dao.models.Author;
import com.hamidur.ss.dao.repos.ArticleRepository;
import com.hamidur.ss.exceptions.custom.MissingAttribute;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ArticleService
{
    private final ArticleRepository articleRepository;
    private final AuthorService authorService;

    @Autowired
    public ArticleService(final ArticleRepository articleRepository, final AuthorService authorService) {
        this.articleRepository = articleRepository;
        this.authorService = authorService;
    }

    @Transactional
    public Article insertArticle(Article article) throws MissingAttribute
    {
        Set<Integer> authorIds = new HashSet<>();
        for (Author author : article.getAuthors())
            if(author.getAuthorId() != null) authorIds.add(author.getAuthorId());

        if(authorIds.isEmpty())
            throw new MissingAttribute("At least 1 author must be associated with article. found="+authorIds.size());

        Article savedArticle = articleRepository.save(article);

        Iterable<Author> iterables = authorService.getAuthorsByIds(authorIds);

        iterables.forEach(author ->
        {
            author.getArticles().add(savedArticle);
            savedArticle.getAuthors().add(author);
        });

        authorService.saveAllAuthors(iterables);

        Article responseArticle = new Article();
        responseArticle.setArticleId(savedArticle.getArticleId());
        responseArticle.setTitle(savedArticle.getTitle());
        responseArticle.setBody(savedArticle.getBody());
        responseArticle.setPublishDate(savedArticle.getPublishDate());
        return responseArticle;
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

    public boolean deleteArticleById(Integer articleId)
    {
        return articleRepository.deleteArticleById(articleId) >= 1;
    }

    public Article updateArticle(Article article) throws MissingAttribute, NotFoundException
    {
        if(article.getArticleId() == null) throw new MissingAttribute("article must contains its id to be updated");
        Optional<Article> optional = articleRepository.findById(article.getArticleId());
        if(optional.isPresent())
        {
            Article article1 = optional.get();
            article1.setTitle(article.getTitle());
            article1.setBody(article.getBody());
            article1.setPublishDate(article.getPublishDate());
            return articleRepository.save(article1);
        }
        else throw new NotFoundException("Not article found with associated id="+article.getArticleId());
    }

    public Set<Article> getAllArticlesWithNoAuthor() throws NotFoundException
    {
        Set<Article> articles = articleRepository.getAllArticlesWithNoAuthor();
        if(articles == null || articles.isEmpty())
            throw new NotFoundException("No articles exists without author to return");
        return articles;
    }
}
