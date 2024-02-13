package com.darmokhval.Backend_part.dto.Authentication.response;

import com.darmokhval.Backend_part.entity.ERole;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<ERole> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, List<ERole> roles) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

}
