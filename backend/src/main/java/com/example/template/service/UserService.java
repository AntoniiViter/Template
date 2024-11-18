package com.example.template.service;

import com.example.template.dto.UserRegistrationDto;
import com.example.template.model.auth.AuthGrantedAuthority;
import com.example.template.model.relational.User;
import com.example.template.repository.AuthGrantedAuthorityRepository;
import com.example.template.repository.UserRepository;
import com.example.template.model.auth.AuthGrantedAuthority.AuthGrantedAuthorities;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {


    private final UserRepository UserRepository;  // PostgreSQL repository for corporations
    private final PasswordEncoder passwordEncoder;
    private final AuthGrantedAuthorityRepository authGrantedAuthorityRepository;

    public UserService(UserRepository UserRepository, PasswordEncoder passwordEncoder, AuthGrantedAuthorityRepository authGrantedAuthorityRepository) {
        this.UserRepository = UserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authGrantedAuthorityRepository = authGrantedAuthorityRepository;
    }

    public User registerNewUser(UserRegistrationDto registrationDto) {
        // Check if a User with the email already exists
        if (UserRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Create a new User instance and save it
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword())); // Encrypt password

        AuthGrantedAuthority authority = authGrantedAuthorityRepository.getReferenceByAuthority(AuthGrantedAuthorities.ROLE_USER.name());
        user.addAuthority(authority);
        return UserRepository.save(user);
    }

    public User registerNewAdmin(UserRegistrationDto registrationDto) {
        // Check if a User with the email already exists
        if (UserRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Create a new User instance and save it
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword())); // Encrypt password

        AuthGrantedAuthority authority = authGrantedAuthorityRepository.getReferenceByAuthority(AuthGrantedAuthorities.ROLE_ADMIN.name());
        user.addAuthority(authority);
        return UserRepository.save(user);
    }
}