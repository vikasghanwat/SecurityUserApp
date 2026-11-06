package com.securityuserapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponseDTO {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Set<String> roles;
}
