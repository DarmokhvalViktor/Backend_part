package com.darmokhval.Backend_part.models.dto.Authentication.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO{
    @NotBlank
    private String username;
    @NotBlank
    private String password;


}
