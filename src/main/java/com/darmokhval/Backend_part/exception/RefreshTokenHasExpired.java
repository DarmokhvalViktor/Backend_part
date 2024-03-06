package com.darmokhval.Backend_part.exception;

public class RefreshTokenHasExpired extends RuntimeException {
    public RefreshTokenHasExpired() {
        super("Refresh token has expired");
    }
}
