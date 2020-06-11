package com.hamidur.ss.exceptions;

import com.hamidur.ss.exceptions.custom.ErrorResponse;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> customHandleNotFound(NotFoundException notFound)
    {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), notFound.getErrorMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> typeMismatchHandler(MethodArgumentTypeMismatchException typeMismatch)
    {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), typeMismatch.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> argumentNotValid(MethodArgumentNotValidException argument)
    {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), argument.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
