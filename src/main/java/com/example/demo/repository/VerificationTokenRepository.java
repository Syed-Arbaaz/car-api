package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
    
    Optional<VerificationToken> findByToken(String token);

}
