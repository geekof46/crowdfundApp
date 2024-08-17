package org.crowdfund;

import org.crowdfund.dao.Customer;
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


@SpringBootApplication
// We use direct @Import instead of @ComponentScan to speed up cold starts
// @ComponentScan(basePackages = "org.example.controller")
@Import({ PingController.class })
public class CrowdFundApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdFundApplication.class, args);
    }

    @Bean
    public DynamoDbTable<Customer> getCustomerDynamoDbTable() {
        // Configure an instance of the standard DynamoDbClient.
        DynamoDbClient standardClient = DynamoDbClient.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

// Use the configured standard client with the enhanced client.
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(standardClient)
                .build();
        return enhancedClient.table("Customer", TableSchema.fromImmutableClass(Customer.class));
    }
}