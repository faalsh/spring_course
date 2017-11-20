package com.example.demo.web.controllers;

import com.example.demo.backend.persistence.domain.backend.Plan;
import com.example.demo.backend.service.PlanService;
import com.example.demo.web.domain.frontend.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlanController {

    private static final Logger LOG = LoggerFactory.getLogger(PlanController.class);

    @Autowired
    private PlanService planService;

    @GetMapping("/api/public/plans")
    public ResponseEntity<StandardResponse> getPublicPlans(){
        LOG.debug("getting plans");
        List<Plan> plans = planService.getPlans();
        return new ResponseEntity<StandardResponse>(new StandardResponse(plans), HttpStatus.OK);
    }

    @GetMapping("/api/plans")
    public ResponseEntity<StandardResponse> getPlans(){
        LOG.debug("getting plans");
        List<Plan> plans = planService.getPlans();
        return new ResponseEntity<StandardResponse>(new StandardResponse(plans), HttpStatus.OK);
    }

}
