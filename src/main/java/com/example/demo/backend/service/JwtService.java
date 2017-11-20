package com.example.demo.backend.service;

import com.example.demo.backend.persistence.domain.backend.Jwt;
import com.example.demo.backend.persistence.repositories.JwtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    private JwtRepository jwtRepository;

    public Jwt findByToken(String token){
        return jwtRepository.findOne(token);
    }

    public void saveToken(String token){
        Jwt jwtToken = new Jwt();
        jwtToken.setToken(token);
        jwtRepository.save(jwtToken);
    }
}
