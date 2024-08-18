package org.crowdfund;

import org.crowdfund.controller.DonationController;
import org.crowdfund.controller.ProjectController;
import org.crowdfund.controller.UserController;
import org.crowdfund.models.UserRole;
import org.crowdfund.models.db.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.crowdfund.controller.PingController;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;


/**
 * application class
 */
@SpringBootApplication
@Import({ PingController.class, DonationController.class, ProjectController.class,
        UserController.class})
public class CrowdFundApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdFundApplication.class, args);
    }
}