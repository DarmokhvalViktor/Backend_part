package com.darmokhval.Backend_part.model.dto.Authentication.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponseDTO {
    private Long id;
    private String username;
    private String role;
    private String email;
}
