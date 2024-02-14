package com.darmokhval.Backend_part.models.dto.Authentication.request;

import lombok.Data;

@Data
public class JwtRefreshRequest {
    private String refreshToken;
}
