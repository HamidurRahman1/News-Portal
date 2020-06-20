package com.hamidur.ss.auth.services;

import com.hamidur.ss.auth.models.User;
import com.hamidur.ss.auth.repos.RoleRepository;
import com.hamidur.ss.auth.repos.UserRepository;
import com.hamidur.ss.exceptions.custom.MissingAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(final UserRepository userRepository, final RoleRepository roleRepository, final PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User insertUser(User user) throws MissingAttribute
    {
        if(user.getRoles() == null || user.getRoles().isEmpty())
            throw new MissingAttribute("At least 1 role be assigned to this user");
        int row = userRepository.insertUserEntity(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getEnabled());
        return null;
    }
}
