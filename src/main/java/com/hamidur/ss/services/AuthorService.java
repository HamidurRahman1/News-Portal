package com.hamidur.ss.services;

import com.hamidur.ss.dao.models.Author;
import com.hamidur.ss.dao.repos.AuthorRepository;
import com.hamidur.ss.exceptions.custom.MissingAttribute;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Set;

@Service
public class AuthorService
{
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public boolean deleteAuthorById(Integer authorId)
    {
        Author author = authorRepository.findByAuthorId(authorId);
        if(author.getUser() == null) return false;
        return authorRepository.deleteAuthorById(authorId, author.getUser().getUserId()) >= 1;
    }

    public Author updateAuthor(Author author) throws MissingAttribute
    {
        if(author.getAuthorId() == null)
            throw new MissingAttribute("Author id must be present to update an author. given="+author.getAuthorId());

        Author author1 = authorRepository.findByAuthorId(author.getAuthorId());
//        author1.setFirstName(author.getFirstName());
//        author1.setLastName(author.getLastName());
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

    public Iterable<Author> getAuthorsByIds(Set<Integer> ids)
    {
        return authorRepository.findAllById(ids);
    }

    public Iterable<Author> saveAllAuthors(Iterable<Author> authors)
    {
        return authorRepository.saveAll(authors);
    }
}
