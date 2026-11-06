package com.securityuserapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDTO {

    @NotBlank
    @Size(min = 3, max = 50)
    String username;

    @NotBlank
    String fullName;

    @NotBlank
    @Email
    String email;

    @NotBlank
    @Size(min = 8, max = 100)
    String password;

}