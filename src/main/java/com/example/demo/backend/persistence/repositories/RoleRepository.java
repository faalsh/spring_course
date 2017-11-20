package com.example.demo.backend.persistence.repositories;

import com.example.demo.backend.persistence.domain.backend.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
