package com.darmokhval.Backend_part.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String name) {
        super(String.format("User %s was not found", name));
    }
}