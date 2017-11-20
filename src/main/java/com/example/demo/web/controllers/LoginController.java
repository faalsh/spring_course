package com.example.demo.web.controllers;

import com.example.demo.backend.persistence.domain.backend.User;
import com.example.demo.backend.service.AuthenticationService;
import com.example.demo.backend.service.UserSecurityService;
import com.example.demo.backend.service.UserService;
import com.example.demo.web.domain.frontend.LoginModel;
import com.example.demo.web.domain.frontend.StandardResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import java.util.Date;

@RestController
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/api/public/login")
    public ResponseEntity<StandardResponse> createAuthenticationToken(@RequestBody LoginModel loginModel) {

        try {
            String token =  authenticationService.login(loginModel.getUsername(), loginModel.getPassword());
            return new ResponseEntity<StandardResponse>(new StandardResponse(token), HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<StandardResponse>(new StandardResponse(false, "Authentication failed"), HttpStatus.UNAUTHORIZED);
        }
    }

}
