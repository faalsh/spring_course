package com.example.demo.backend.service;

import com.example.demo.backend.persistence.domain.backend.Plan;
import com.example.demo.backend.persistence.domain.backend.User;
import com.example.demo.backend.persistence.domain.backend.UserRole;
import com.example.demo.backend.persistence.repositories.PlanRepository;
import com.example.demo.backend.persistence.repositories.RoleRepository;
import com.example.demo.backend.persistence.repositories.UserRepository;
import com.example.demo.enums.PlansEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user, PlansEnum plansEnum, Set<UserRole> userRoles) {

        String encryptedPassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        Plan plan = new Plan(plansEnum);

        if (!planRepository.exists(plansEnum.getId())) {
            plan = planRepository.save(plan);
        }

        user.setPlan(plan);

        for (UserRole ur : userRoles) {
            roleRepository.save(ur.getRole());
        }

        user.getUserRoles().addAll(userRoles);
        user = userRepository.save(user);

        return user;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void updateUserPassword(long userId, String password){
        password = passwordEncoder.encode(password);
        userRepository.updateUserPassword(userId, password);
        LOG.debug("password update successfull for user {}", userId);
    }

    @Transactional
    public void updateUserPassword(String email, String password) {
        password = passwordEncoder.encode(password);
        userRepository.updateUserPassword(email, password);
        LOG.debug("password update successfull for user {}", email);
    }


}
