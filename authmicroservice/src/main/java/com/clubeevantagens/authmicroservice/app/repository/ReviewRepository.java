package com.clubeevantagens.authmicroservice.app.repository;

import com.clubeevantagens.authmicroservice.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}