package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, String> {
    List<Token> findByUserId(long userId);
}