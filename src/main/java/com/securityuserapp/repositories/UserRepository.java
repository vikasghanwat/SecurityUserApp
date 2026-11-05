package com.securityuserapp.repositories;

import com.securityuserapp.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends JpaRepository<UserEntity, Long> {

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUsernameIgnoreCaseAndIdNot(
            String username, Long id);

    boolean existsByEmailIgnoreCaseAndIdNot(
            String email, Long id);
}
