package com.darmokhval.Backend_part.exceptions;

public class RefreshTokenHasExpired extends RuntimeException {
    public RefreshTokenHasExpired() {
        super("Refresh token has expired");
    }
}
