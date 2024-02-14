package com.darmokhval.Backend_part.models.dto.Authentication.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicRequestDTO {
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;

    public BasicRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
