package com.securityuserapp.services;

import com.securityuserapp.dto.UserRequestDTO;
import com.securityuserapp.dto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);

    Page<UserResponseDTO> getAllUsers(Pageable pageable);

    UserResponseDTO getUserById(Long userId);

    UserResponseDTO updateUser(
            Long userId, UserRequestDTO request);

    void deleteUser(Long userId);
}
