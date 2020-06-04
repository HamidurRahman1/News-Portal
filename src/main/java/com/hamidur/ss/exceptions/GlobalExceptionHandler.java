package com.hamidur.ss.exceptions;

import com.hamidur.ss.exceptions.custom.ErrorResponse;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> customHandleNotFound(NotFoundException notFound)
    {
        System.out.println(notFound.getErrorMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), notFound.getErrorMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
