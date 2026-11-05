package com.securityuserapp.dto;

import com.securityuserapp.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Set<Role> roles;
    private boolean enabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}