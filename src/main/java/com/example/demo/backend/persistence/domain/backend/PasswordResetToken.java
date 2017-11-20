package com.example.demo.backend.persistence.domain.backend;

import com.example.demo.backend.persistence.converters.LocalDateTimeAttibuteConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken implements Serializable{

    private static final int DEFAULT_TOKEN_LENGTH = 120;
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetToken.class);

    public PasswordResetToken(){

    }

    public PasswordResetToken(String token, User user, LocalDateTime creationDateTime, int expirationInMinutes){
        if((null == token) || (null == user) || (null == creationDateTime)){
            throw new IllegalArgumentException(("token, user and exiration date cannot be null"));
        }

        if(expirationInMinutes == 0){
            LOG.warn("token expiration is 0, assigning default value of {}", DEFAULT_TOKEN_LENGTH);
            expirationInMinutes = DEFAULT_TOKEN_LENGTH;
        }

        this.token = token;
        this.user = user;
        expiryDate = creationDateTime.plusMinutes(expirationInMinutes);

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expiry_date")
    @Convert(converter = LocalDateTimeAttibuteConverter.class)
    private LocalDateTime expiryDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordResetToken that = (PasswordResetToken) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
