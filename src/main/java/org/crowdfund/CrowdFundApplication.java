package org.crowdfund;

//import org.crowdfund.controller.DonationController;
import org.crowdfund.controller.ProjectController;
import org.crowdfund.controller.UserController;
import org.crowdfund.models.UserRole;
import org.crowdfund.models.db.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.crowdfund.controller.PingController;


/**
 * application class
 */
@SpringBootApplication
@Import({ PingController.class, ProjectController.class,
        UserController.class})
public class CrowdFundApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrowdFundApplication.class, args);
    }
}