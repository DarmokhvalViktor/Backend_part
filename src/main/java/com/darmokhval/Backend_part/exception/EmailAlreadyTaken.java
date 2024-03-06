package com.darmokhval.Backend_part.exception;

public class EmailAlreadyTaken extends RuntimeException {
    public EmailAlreadyTaken(String email) {
        super(String.format("Email %s is already taken", email));
    }
}
