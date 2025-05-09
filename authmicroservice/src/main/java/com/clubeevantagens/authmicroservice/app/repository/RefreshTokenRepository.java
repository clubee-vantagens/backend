package com.clubeevantagens.authmicroservice.app.repository;

import com.clubeevantagens.authmicroservice.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    void deleteByUserId(Long userId);
}