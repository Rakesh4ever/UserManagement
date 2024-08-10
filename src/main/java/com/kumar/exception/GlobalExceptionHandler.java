package com.kumar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author RakeshKumar created on 10/08/24
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value =UserNotFoundException.class )
    public ResponseEntity<String> userNotFound(UserNotFoundException ex){
        return new ResponseEntity<String>("An error occured, "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
