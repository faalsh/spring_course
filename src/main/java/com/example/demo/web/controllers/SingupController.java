package com.example.demo.web.controllers;

import com.example.demo.backend.persistence.domain.backend.Plan;
import com.example.demo.backend.persistence.domain.backend.Role;
import com.example.demo.backend.persistence.domain.backend.User;
import com.example.demo.backend.persistence.domain.backend.UserRole;
import com.example.demo.backend.service.PlanService;
import com.example.demo.backend.service.UserService;
import com.example.demo.enums.PlansEnum;
import com.example.demo.enums.RolesEnum;
import com.example.demo.utils.UserUtils;
import com.example.demo.web.domain.frontend.ProAccountPayload;
import com.example.demo.web.domain.frontend.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class SingupController {

    private static final Logger LOG = LoggerFactory.getLogger(SingupController.class);

    @Autowired
    private PlanService planService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/public/signup/{planId}")
    public ResponseEntity<StandardResponse> signUp(@Valid @RequestBody ProAccountPayload payload,
                                 @PathVariable int planId){

        // check if plan is valid
        if(planId != PlansEnum.BASIC.getId() && planId != PlansEnum.PRO.getId()){
            LOG.error("invalid plan id {}", planId);
            return new ResponseEntity(new StandardResponse(false, "Invalid plan id"), HttpStatus.BAD_REQUEST);
        }

        // check if username already exists
        if(userService.findByUserName(payload.getUserName()) != null){
            LOG.error("username {} already exists", payload.getUserName());
            return new ResponseEntity(new StandardResponse(false, "Username already exists"), HttpStatus.BAD_REQUEST);
        }

        // check if email already exists
        if(userService.findByEmail(payload.getEmail()) != null){
            LOG.error("email {} already exists", payload.getEmail());
            return new ResponseEntity(new StandardResponse(false, "Email already exists"), HttpStatus.BAD_REQUEST);
        }

        // transform user payload to domain object
        LOG.debug("Transforming user payload to domain object");
        User user = UserUtils.fromWebUserToDomainUser(payload);

        // set plans and roles to the user
        LOG.debug("Retrieving plan from database");
        Plan selectedPlan = planService.findPlanById(planId);

        if(null == selectedPlan){
            LOG.error("plan Id {} cannot be found", planId);
            return new ResponseEntity(new StandardResponse(false, "Invalid plan id"), HttpStatus.BAD_REQUEST);
        }

        user.setPlan(selectedPlan);

        User registeredUser = null;

        Set<UserRole> roles = new HashSet<>();

        if(planId == PlansEnum.BASIC.getId()){
            roles.add(new UserRole(user, new Role(RolesEnum.BASIC)));
            registeredUser = userService.createUser(user, PlansEnum.BASIC, roles);
        } else if (planId == PlansEnum.PRO.getId()){
            roles.add(new UserRole(user, new Role(RolesEnum.BASIC)));
            registeredUser = userService.createUser(user, PlansEnum.PRO, roles);
        }

        LOG.info("User created successfully");

        return new ResponseEntity(new StandardResponse(true), HttpStatus.OK);
    }

}
