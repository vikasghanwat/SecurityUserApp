package com.securityuserapp.services;

import com.securityuserapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Load user by email for Spring Security
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User with email " + email + " not found"));
    }
}
