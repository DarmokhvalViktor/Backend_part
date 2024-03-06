package com.darmokhval.Backend_part.exception;

public class UsernameAlreadyTaken extends RuntimeException{
    public UsernameAlreadyTaken(String name) {
        super(String.format("Username %s is already taken", name));
    }
}
