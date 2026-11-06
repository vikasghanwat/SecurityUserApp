package com.securityuserapp.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "app_users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_app_users_username",
                        columnNames = "username"
                ),
                @UniqueConstraint(
                        name = "uk_app_users_email",
                        columnNames = "email"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends AuditableEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "app_user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false)
    private boolean enabled = true;

    public UserEntity(Long id, String username, String fullName, String email) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
    }

    //UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}