package com.example.demo;

import com.example.demo.backend.persistence.domain.backend.Role;
import com.example.demo.backend.persistence.domain.backend.User;
import com.example.demo.backend.persistence.domain.backend.UserRole;
import com.example.demo.backend.service.PlanService;
import com.example.demo.backend.service.UserService;
import com.example.demo.enums.PlansEnum;
import com.example.demo.enums.RolesEnum;
import com.example.demo.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

    private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PlanService planService;

    @Value("${webmaster.username}")
    private String webmasterUsername;
    @Value("${webmaster.password}")
    private String webmasterPassword;
    @Value("${webmaster.email}")
    private String webmasterEmail;

	public static void main(String[] args) {
		LOG.debug("starting main");
		SpringApplication.run(DemoApplication.class, args);
	}

    @Override
    public void run(String... strings) throws Exception {

	    LOG.info("creating basic and pro plans in the database");
        planService.createPlan(PlansEnum.BASIC.getId());
        planService.createPlan(PlansEnum.PRO.getId());

        User user = UserUtils.createBasicUser(webmasterUsername, webmasterEmail);
        user.setPassword(webmasterPassword);
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, new Role(RolesEnum.ADMIN)));
        LOG.debug("creating user {}", user);
        userService.createUser(user, PlansEnum.PRO, userRoles);
        LOG.info("User {} created", user.getUsername());
    }
}
