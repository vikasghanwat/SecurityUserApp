package com.securityuserapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private String message;
    private long expiresIn;
    private String accessToken;
    private String tokenType;

}