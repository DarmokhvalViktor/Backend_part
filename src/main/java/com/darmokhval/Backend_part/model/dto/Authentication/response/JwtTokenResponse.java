package com.darmokhval.Backend_part.model.dto.Authentication.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenResponse {
    private String accessToken;
    private String refreshToken;
}
