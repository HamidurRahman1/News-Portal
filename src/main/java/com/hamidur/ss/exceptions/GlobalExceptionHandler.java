package com.hamidur.ss.exceptions;

import com.hamidur.ss.exceptions.custom.ConstraintViolationException;
import com.hamidur.ss.exceptions.custom.ErrorResponse;
import com.hamidur.ss.exceptions.custom.MissingAttribute;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import com.hamidur.ss.exceptions.restSecurityExceptions.UserNameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<ErrorResponse> argumentNotValid(MethodArgumentNotValidException ex)
    {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), errors.toString(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constrainViolation(ConstraintViolationException cv)
    {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), cv.getErrorMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNameNotFoundException.class)
    public ResponseEntity<ErrorResponse> usernameNotFoundException(UserNameNotFoundException e)
    {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), e.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = MissingAttribute.class)
    public ResponseEntity<ErrorResponse> missingAttribute(MissingAttribute ma)
    {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ma.getErrorMessage(), HttpStatus.PARTIAL_CONTENT.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.PARTIAL_CONTENT);
    }
}
