package org.crowdfund.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;

@ToString
@Builder
@Getter
public class UserDTO {
    @NonNull
    private String userId;
    private String name;
    private String alias;
    private UserRole role;
    private Instant createDate;
    private String bankAccountNumber;
    private Instant updateDate;
}
