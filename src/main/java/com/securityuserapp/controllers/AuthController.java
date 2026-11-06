package com.securityuserapp.controllers;

import com.securityuserapp.dto.SignupRequestDTO;
import com.securityuserapp.dto.SignupResponseDTO;
import com.securityuserapp.dto.LoginRequestDTO;
import com.securityuserapp.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Signup API
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signUp(
            @Valid @RequestBody SignupRequestDTO signupRequestDTO) {

        SignupResponseDTO response =
                authService.signUp(signupRequestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Login API
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        String token = authService.login(loginRequestDTO);

        return ResponseEntity.ok(token);
    }
}
