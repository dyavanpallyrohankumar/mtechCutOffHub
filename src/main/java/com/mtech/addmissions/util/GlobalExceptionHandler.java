package com.mtech.addmissions.util;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mtech.addmissions.exception.Credentials;
import com.mtech.addmissions.exception.ResourseAlreadyExist;
import com.mtech.addmissions.exception.ResourseNotExist;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourseNotExist.class)
    public ResponseEntity<ErrorResponse> ResourseNotExistHandler(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), HttpStatus.NO_CONTENT, e.getMessage(),
                "Message From Resourse Not Found Exception", null), HttpStatus.OK);
    }

    @ExceptionHandler(ResourseAlreadyExist.class)
    public ResponseEntity<ErrorResponse> ResourseAlreadyExistHandler(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), HttpStatus.NO_CONTENT, e.getMessage(),
                "Message From Resourse Not Found Exception", null), HttpStatus.OK);
    }

    @ExceptionHandler(Credentials.class)
    public ResponseEntity<ErrorResponse> CredentialsHandler(Exception e) {
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now(), HttpStatus.NO_CONTENT, e.getMessage(),
                "Message From Resourse Not Found Exception", null), HttpStatus.OK);
    }
}
