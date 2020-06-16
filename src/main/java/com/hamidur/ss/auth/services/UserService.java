package com.hamidur.ss.auth.services;

import com.hamidur.ss.auth.models.User;
import com.hamidur.ss.auth.repos.UserRepository;
import com.hamidur.ss.exceptions.custom.MissingAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User insertUser(User user) throws MissingAttribute
    {
        if(user.getRoles() == null || user.getRoles().isEmpty())
            throw new MissingAttribute("At least 1 role be assigned to this user");
        return userRepository.save(user);
    }
}
