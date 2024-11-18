package com.example.template.config;

import com.example.template.dto.UserRegistrationDto;
import com.example.template.model.auth.AuthGrantedAuthority;
import com.example.template.model.auth.AuthGrantedAuthority.AuthGrantedAuthorities;
import com.example.template.repository.AuthGrantedAuthorityRepository;
import com.example.template.repository.UserRepository;
import com.example.template.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AuthInitializerConfig {

    private final UserService userService;

    private final AuthGrantedAuthorityRepository authGrantedAuthorityRepository;

    public AuthInitializerConfig(UserService userService, UserRepository userRepository, AuthGrantedAuthorityRepository authGrantedAuthorityRepository) {
        this.userService = userService;
        this.authGrantedAuthorityRepository = authGrantedAuthorityRepository;
    }

    // initialize the admin in DB
        @Bean
        public CommandLineRunner initializeJpaData(UserRepository userRepository) {
            return (args) -> {
                // Check if the admin user already exists
                if(userRepository.findByEmail("admin@example.com").isPresent()) {
                    return;
                }

                //Authorities
                // Create and save initial authority ADMIN for the admin
                AuthGrantedAuthority adminAuth = new AuthGrantedAuthority();
                adminAuth.setAuthority(AuthGrantedAuthorities.ROLE_ADMIN.name());
                authGrantedAuthorityRepository.save(adminAuth);

                // Create and save initial authority USER for users
                // Further other authorities inside a corporation can be declared
                AuthGrantedAuthority userAuth = new AuthGrantedAuthority();
                userAuth.setAuthority(AuthGrantedAuthorities.ROLE_USER.name());
                authGrantedAuthorityRepository.save(userAuth);

                UserRegistrationDto adminDTO = new UserRegistrationDto();
                adminDTO.setEmail("admin@example.com");
                adminDTO.setPassword("password");

                // Save the admin and the associated authority
                userService.registerNewAdmin(adminDTO);
            };
        }
}
