package com.hamidur.ss.auth.services;

import com.hamidur.ss.auth.models.Role;
import com.hamidur.ss.auth.models.User;
import com.hamidur.ss.auth.repos.RoleRepository;
import com.hamidur.ss.auth.repos.UserRepository;
import com.hamidur.ss.exceptions.custom.ConstraintViolationException;
import com.hamidur.ss.exceptions.custom.MissingAttribute;
import com.hamidur.ss.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService
{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorService authorService;

    @Autowired
    public UserService(final UserRepository userRepository, final RoleRepository roleRepository,
                       final PasswordEncoder passwordEncoder, final AuthorService authorService)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorService = authorService;
    }

    @Transactional
    public User insertUser(User user) throws MissingAttribute
    {
        if(user.getRoles() == null || user.getRoles().isEmpty())
            throw new MissingAttribute("At least 1 role be assigned to this user");
        try
        {
            if(userRepository.insertUserEntity(user.getEmail(), passwordEncoder.encode(user.getPassword()), user.getEnabled()) >= 1)
            {
                User savedUser = userRepository.getUserByUsername(user.getEmail());
                Set<Role> roles = roleRepository.getAll();
                Set<Role> assignedRoles = new HashSet<>();
                roles.forEach(role -> {
                    if(user.getRoles().contains(role)) assignedRoles.add(role);
                });
                savedUser.setRoles(assignedRoles);
                return userRepository.save(savedUser);
            }
            return null;
        }
        catch (DataIntegrityViolationException ex)
        {
            throw new ConstraintViolationException("A user with email="+user.getEmail()+" already exists");
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
        Integer authorId = userRepository.isUserAnAuthor(userId);
        if(authorId == null)
            return userRepository.deleteUserById(userId) >= 1;
        return authorService.deleteAuthorById(authorId);
    }
}
