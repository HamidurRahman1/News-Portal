package com.hamidur.np.auth.services;

import com.hamidur.np.auth.models.User;
import com.hamidur.np.auth.repos.UserRepository;
import com.hamidur.np.exceptions.custom.AccountDisabledException;
import com.hamidur.np.exceptions.custom.ConstraintViolationException;
import com.hamidur.np.exceptions.custom.MissingAttribute;
import com.hamidur.np.exceptions.custom.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService
{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addArticleToAuthor(Integer userId, Integer articleId) throws ConstraintViolationException
    {
        try{
            userRepository.addArticleToAuthor(userId, articleId);
        }
        catch (DataIntegrityViolationException ex)
        {
            throw new ConstraintViolationException("Author is already associated with this Article or invalid userId/article given or user does not have Author role");
        }
    }

    public Set<User> getAllAuthors() throws NotFoundException
    {
        Set<User> authors = userRepository.getAllAuthors();
        if(authors == null || authors.isEmpty())
            throw new NotFoundException("No authors found to return");
        return authors;
    }

    @Transactional
    public User insertUser(User user) throws MissingAttribute
    {
        try
        {
            user.setEnabled(true);
            userRepository.signUpWithUserRole(user.getFirstName(), user.getLastName(), user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getEnabled());
            return userRepository.getUserByUsername(user.getUsername());
        }
        catch (DataIntegrityViolationException ex)
        {
            throw new ConstraintViolationException("A user with username="+user.getUsername()+" already exists");
        }
    }

    public boolean revokeRole(Integer userId, Integer roleId)
    {
        return userRepository.revokeRole(userId, roleId) >= 1;
    }

    public boolean addRole(Integer userId, Integer roleId) throws ConstraintViolationException
    {
        try
        {
            return userRepository.addRole(userId, roleId) >= 1;
        }
        catch (DataIntegrityViolationException e)
        {
            if(e.getMessage().toUpperCase().contains("USERS_ROLES FOREIGN KEY"))
                throw new ConstraintViolationException("Invalid id provided for userId or roleId. provided userId="+userId+", roleId="+roleId);
            else if(e.getMessage().toUpperCase().contains("PRIMARY_KEY"))
                throw new ConstraintViolationException("User with id="+userId+" already has the role with roleId="+roleId);
            else
                throw e;
        }
    }

    public boolean deleteUserById(Integer userId)
    {
        return userRepository.deleteUserById(userId) >= 1;
    }

    public User updateUser(User user) throws MissingAttribute, NotFoundException, ConstraintViolationException
    {
        if(user.getUserId() == null)
            throw new MissingAttribute("userId must be present to update User attributes");
        Optional<User> dbUser = userRepository.findById(user.getUserId());
        if(!dbUser.isPresent())
            throw new NotFoundException("No user found with userId="+user.getUserId());
        else if(!dbUser.get().getEnabled())
            throw new AccountDisabledException("User with userId="+user.getUserId()+" cannot be updated since account is not activated yet");
        else
        {
            try
            {
                User user1 = dbUser.get();
                user1.setFirstName(user.getFirstName());
                user1.setLastName(user.getLastName());
                user1.setUsername(user.getUsername());
                user1.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user1);
            }
            catch (DataIntegrityViolationException ex)
            {
                throw new ConstraintViolationException("username="+user.getUsername()+" is already in use");
            }
        }
    }

    public boolean deactivateUserAccount(Integer userId) throws NotFoundException
    {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent())
            throw new NotFoundException("No user with id="+userId+" found to deactivate associated account");
        else
        {
            User user = optionalUser.get();
            user.setEnabled(false);
            return !userRepository.save(user).getEnabled();
        }
    }

    public User getAuthorByUserId(Integer userId) throws NotFoundException
    {
        User user = userRepository.getAuthorByUserId(userId);
        if(user == null)
            throw new NotFoundException("No author found with userId="+userId);
        return user;
    }

    public boolean removeAuthorRole(Integer userId)
    {
        User user = userRepository.getAuthorByUserId(userId);
        if(user == null)
            throw new NotFoundException("Author role cannot be revoked since User with userId="+userId+" does not have author role");
        return userRepository.removeAuthorRole(userId) >= 1;
    }
}
