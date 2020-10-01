package com.hamidur.np.exceptions.custom;

import org.springframework.security.core.AuthenticationException;

public class AccountDisabledException extends AuthenticationException {
    public AccountDisabledException(String msg) {
        super(msg);
    }
}
