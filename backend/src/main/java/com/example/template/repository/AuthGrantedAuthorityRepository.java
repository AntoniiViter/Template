package com.example.template.repository;

import com.example.template.model.auth.AuthGrantedAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthGrantedAuthorityRepository extends JpaRepository<AuthGrantedAuthority, Long> {
    AuthGrantedAuthority getReferenceByAuthority(String authority);
}
