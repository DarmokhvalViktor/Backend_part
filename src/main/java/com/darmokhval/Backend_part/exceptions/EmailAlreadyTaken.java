package com.darmokhval.Backend_part.exceptions;

public class EmailAlreadyTaken extends RuntimeException {
    public EmailAlreadyTaken(String email) {
        super(String.format("Email %s is already taken", email));
    }
}
