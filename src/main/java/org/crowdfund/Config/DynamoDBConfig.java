package org.crowdfund.Config;

import lombok.NonNull;
import org.crowdfund.models.db.Donation;
import org.crowdfund.models.db.Project;
import org.crowdfund.models.db.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {

    @Bean
    public DynamoDbClient getDynamoDbClient(){
       return DynamoDbClient.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient(@NonNull final DynamoDbClient dynamoDbClient){
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Primary
    @Bean
    public DynamoDbTable<User> getUserTable(@NonNull final DynamoDbEnhancedClient dbEnhancedClient){
        return  dbEnhancedClient.table(User.TABLE_NAME, TableSchema.fromImmutableClass(User.class));
    }

    @Primary
    @Bean
    public DynamoDbTable<Project> getProjectTable(@NonNull final DynamoDbEnhancedClient dbEnhancedClient){
        return  dbEnhancedClient.table(Project.TABLE_NAME, TableSchema.fromImmutableClass(Project.class));
    }

    @Primary
    @Bean
    public DynamoDbTable<Donation> getDonationTable(@NonNull final DynamoDbEnhancedClient dbEnhancedClient){
        return  dbEnhancedClient.table(Donation.TABLE_NAME, TableSchema.fromImmutableClass(Donation.class));
    }
}
