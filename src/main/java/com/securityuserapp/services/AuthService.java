package com.securityuserapp.services;

import com.securityuserapp.dto.SignupRequestDTO;
import com.securityuserapp.dto.SignupResponseDTO;
import com.securityuserapp.dto.LoginRequestDTO;

import com.securityuserapp.entities.Role;
import com.securityuserapp.entities.UserEntity;
import com.securityuserapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // Signup
    @Transactional
    public SignupResponseDTO signUp(SignupRequestDTO signupRequestDTO) {

        if (userRepository.existsByEmailIgnoreCase(
                signupRequestDTO.getEmail())) {

            throw new BadCredentialsException(
                    "User with email already exists: "
                            + signupRequestDTO.getEmail());
        }

        UserEntity user = modelMapper.map(
                signupRequestDTO, UserEntity.class);

        // Encrypt password before saving
        user.setPasswordHash(
                passwordEncoder.encode(signupRequestDTO.getPassword()));

        // Assign default role
        user.setRoles(java.util.Set.of(Role.USER));
        user.setEnabled(true);

        UserEntity savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, SignupResponseDTO.class);
    }

    // Login
    public String login(LoginRequestDTO loginRequestDTO) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequestDTO.getEmail(),
                                loginRequestDTO.getPassword()
                        )
                );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        return jwtService.generateToken(user);
    }

}