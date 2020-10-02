package com.hamidur.np.exceptions.custom;

public class MissingAttribute extends BaseException
{
    public MissingAttribute(String message) {
        super(message);
    }

    public MissingAttribute(String message, int status) {
        super(message, status);
    }
}
