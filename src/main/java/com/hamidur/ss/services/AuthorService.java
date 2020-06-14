package com.hamidur.ss.services;

import com.hamidur.ss.dao.models.Author;
import com.hamidur.ss.dao.repos.AuthorRepository;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthorService
{
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public boolean deleteAuthorById(Integer authorId)
    {
        return authorRepository.deleteByAuthorId(authorId) >= 1;
    }

    public Author insertAuthor(Author author)
    {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author author) throws NotFoundException
    {
        if(author.getAuthorId() == null)
            throw new NotFoundException("Author id must be present in to update an author. given="+author.getAuthorId());

        Author author1 = authorRepository.findByAuthorId(author.getAuthorId());
        author1.setFirstName(author.getFirstName());
        author1.setLastName(author.getLastName());
        return authorRepository.save(author1);
    }

    public Set<Author> getAllAuthors() throws NotFoundException
    {
        Set<Author> authors = authorRepository.findAll();
        if(authors == null || authors.isEmpty())
            throw new NotFoundException("No authors found to return");
        return authors;
    }

    public Author getAuthorById(Integer authorId) throws NotFoundException
    {
        Author author = authorRepository.findByAuthorId(authorId);
        if(author == null)
            throw new NotFoundException("No author found associated with authorId="+authorId);
        return author;
    }
}
