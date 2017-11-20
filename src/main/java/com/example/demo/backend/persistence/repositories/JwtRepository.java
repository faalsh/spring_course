package com.example.demo.backend.persistence.repositories;

import com.example.demo.backend.persistence.domain.backend.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtRepository extends JpaRepository<Jwt, String> {
}
