package com.securityuserapp.services;

import com.securityuserapp.dto.UserRequestDTO;
import com.securityuserapp.dto.UserResponseDTO;
import com.securityuserapp.entities.UserEntity;
import com.securityuserapp.exceptions.DuplicateUserException;
import com.securityuserapp.exceptions.ResourceNotFoundException;
import com.securityuserapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // CREATE
    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {

        String username = normalizeUsername(request.getUsername());
        String email = normalizeEmail(request.getEmail());

        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new DuplicateUserException("Username already exists: " + username);
        }

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateUserException("Email already exists: " + email);
        }

        UserEntity user = new UserEntity();

        user.setUsername(username);
        user.setFullName(request.getFullName().trim());
        user.setEmail(email);
        user.setRoles(new HashSet<>(request.getRoles()));
        user.setEnabled(request.isEnabled());

        return mapToResponse(userRepository.save(user));
    }

    // GET ALL WITH PAGINATION
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable).map(this::mapToResponse);
    }

    // GET BY ID
    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long userId) {

        return mapToResponse(findUserOrThrow(userId));
    }

    // UPDATE
    @Override
    public UserResponseDTO updateUser(Long userId, UserRequestDTO request) {

        UserEntity existingUser = findUserOrThrow(userId);

        String username = normalizeUsername(request.getUsername());
        String email = normalizeEmail(request.getEmail());

        if (userRepository.existsByUsernameIgnoreCaseAndIdNot(username, userId)) {
            throw new DuplicateUserException("Username already exists: " + username);
        }

        if (userRepository.existsByEmailIgnoreCaseAndIdNot(email, userId)) {
            throw new DuplicateUserException("Email already exists: " + email);
        }

        existingUser.setUsername(username);
        existingUser.setFullName(request.getFullName().trim());
        existingUser.setEmail(email);
        existingUser.setRoles(new HashSet<>(request.getRoles()));
        existingUser.setEnabled(request.isEnabled());

        return mapToResponse(userRepository.save(existingUser));
    }

    // DELETE
    @Override
    public void deleteUser(Long userId) {

        UserEntity user = findUserOrThrow(userId);

        userRepository.delete(user);
    }

    // COMMON LOOKUP
    private UserEntity findUserOrThrow(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    // NORMALIZE USERNAME
    private String normalizeUsername(String username) {
        return username.trim();
    }

    // NORMALIZE EMAIL
    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    // ENTITY TO RESPONSE DTO
    private UserResponseDTO mapToResponse(UserEntity user) {

        UserResponseDTO response = new UserResponseDTO();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRoles(new HashSet<>(user.getRoles()));
        response.setEnabled(user.isEnabled());

        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        response.setCreatedBy(user.getCreatedBy());
        response.setUpdatedBy(user.getUpdatedBy());

        return response;
    }
}