package com.darmokhval.Backend_part.model.dto.Authentication.request;

import lombok.Data;

@Data
public class JwtRefreshRequest {
    private String refreshToken;
}
