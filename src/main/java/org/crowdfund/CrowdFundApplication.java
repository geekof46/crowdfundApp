package org.crowdfund;

import org.crowdfund.controller.DonationController;
import org.crowdfund.controller.PingController;
import org.crowdfund.controller.ProjectController;
import org.crowdfund.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


/**
 * application class
 */
@SpringBootApplication
@Import({PingController.class, ProjectController.class,
        UserController.class, DonationController.class})
public class CrowdFundApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrowdFundApplication.class, args);
    }
}