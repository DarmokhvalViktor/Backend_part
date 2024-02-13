package com.darmokhval.Backend_part.dto.Authentication;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SingUpDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private int yearOfBirth;
}
