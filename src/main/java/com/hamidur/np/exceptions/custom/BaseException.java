package com.hamidur.np.exceptions.custom;

import java.time.LocalDateTime;

public abstract class BaseException extends RuntimeException
{
    private LocalDateTime timestamp;
    private String errorMessage;
    private int status;

    public BaseException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    public BaseException(String errorMessage, int status) {
        super();
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public BaseException(LocalDateTime timestamp, String errorMessage, int status) {
        super();
        this.errorMessage = errorMessage;
        this.timestamp = timestamp;
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
