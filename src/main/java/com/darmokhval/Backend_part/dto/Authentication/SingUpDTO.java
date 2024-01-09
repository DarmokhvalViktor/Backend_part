package com.darmokhval.Backend_part.dto.Authentication;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SingUpDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private int yearOfBirth;
}
