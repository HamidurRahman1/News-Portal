package com.hamidur.np.exceptions.custom;

public class ConstraintViolationException extends BaseException
{
    public ConstraintViolationException(String message) {
        super(message);
    }

    public ConstraintViolationException(String message, int status) {
        super(message, status);
    }
}
