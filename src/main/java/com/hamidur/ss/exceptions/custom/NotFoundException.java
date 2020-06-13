package com.hamidur.ss.exceptions.custom;

public class NotFoundException extends BaseException
{
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, int status) {
        super(message, status);
    }
}
