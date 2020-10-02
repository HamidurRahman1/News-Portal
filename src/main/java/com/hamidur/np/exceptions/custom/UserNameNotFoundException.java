package com.hamidur.np.exceptions.custom;

import org.springframework.security.core.AuthenticationException;

public class UserNameNotFoundException extends AuthenticationException
{
    public UserNameNotFoundException(String msg) {
        super(msg);
    }
}
