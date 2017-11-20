package com.example.demo.backend.service;

import com.example.demo.backend.persistence.domain.backend.Plan;
import com.example.demo.backend.persistence.repositories.PlanRepository;
import com.example.demo.enums.PlansEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PlanService {

    @Autowired
    PlanRepository planRepository;

    public List<Plan> getPlans(){
        return planRepository.findAll();
    }

    public Plan findPlanById(int planId){
        return planRepository.findOne(planId);
    }

    @Transactional
    public Plan createPlan(int planId){
        Plan plan = null;
        if(planId == 1){
            plan = planRepository.save(new Plan(PlansEnum.BASIC));
        } else if(planId == 2){
            plan = planRepository.save(new Plan(PlansEnum.PRO));
        } else {
            throw new IllegalArgumentException("Plan id " + planId + " not recognized");
        }

        return plan;
    }
}
