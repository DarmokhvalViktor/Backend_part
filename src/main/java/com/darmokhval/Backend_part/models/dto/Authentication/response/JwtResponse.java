package com.darmokhval.Backend_part.models.dto.Authentication.response;

import com.darmokhval.Backend_part.models.entity.ERole;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String role;

    public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email, String role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
