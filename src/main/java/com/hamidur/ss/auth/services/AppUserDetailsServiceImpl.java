package com.hamidur.ss.auth.services;

import com.hamidur.ss.auth.models.User;
import com.hamidur.ss.auth.repos.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(s);
        if(user == null) throw new UsernameNotFoundException("No such user with username="+s);
        if(!user.getEnabled()) throw new DisabledException("User is disabled, needs to verify their account");
        return new AppUserDetails(user);
    }
}
