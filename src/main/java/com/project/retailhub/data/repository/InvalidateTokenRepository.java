package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.InvalidateToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidateTokenRepository extends JpaRepository<InvalidateToken, String> {
}