package com.example.demo.backend.persistence.repositories;

import com.example.demo.backend.persistence.domain.backend.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends JpaRepository<Plan, Integer> {

}
