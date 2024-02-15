package com.darmokhval.Backend_part.exceptions;

public class InvalidRefreshTokenSignature extends RuntimeException {
    public InvalidRefreshTokenSignature() {
        super("Token signature doesn't match refresh token");
    }
}
