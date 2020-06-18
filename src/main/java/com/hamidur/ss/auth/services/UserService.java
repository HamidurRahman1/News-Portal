package com.hamidur.ss.auth.services;

import com.hamidur.ss.auth.models.User;
import com.hamidur.ss.auth.repos.RoleRepository;
import com.hamidur.ss.auth.repos.UserRepository;
import com.hamidur.ss.exceptions.custom.MissingAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(final UserRepository userRepository, final RoleRepository roleRepository)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User insertUser(User user) throws MissingAttribute
    {
        if(user.getRoles() == null || user.getRoles().isEmpty())
            throw new MissingAttribute("At least 1 role be assigned to this user");
        System.out.println(user);
        userRepository.insertUserEntity(user.getUsername(), user.getPassword(), user.isEnabled());
        return null;
    }
}
