package com.example.demo.backend.persistence.domain.backend;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Jwt implements Serializable {

    private static final long serialVersionUID = 1L;

    public Jwt(){

    }

    @Id
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jwt jwt = (Jwt) o;

        return token.equals(jwt.token);
    }

    @Override
    public int hashCode() {
        return token.hashCode();
    }
}
