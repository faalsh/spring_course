package com.example.demo.backend.persistence.repositories;


import com.example.demo.backend.persistence.domain.backend.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
    public User findByEmail(String email);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :userId")
    public void updateUserPassword(@Param("userId") long userId, @Param("password") String password);

    @Modifying
    @Query("update User u set u.password = :password where u.email = :email")
    public void updateUserPassword(@Param("email") String email, @Param("password") String password);
}
