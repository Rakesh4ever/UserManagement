package com.kumar.exception;

/**
 * @author RakeshKumar created on 10/08/24
 */

public class UserNotFoundException extends  RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }

}
