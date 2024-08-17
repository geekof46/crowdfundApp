package org.crowdfund.models.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.crowdfund.models.UserRole;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbImmutable(builder = User.UserBuilder.class)
public class User {
    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("UserID")}))
    private String userId;
    private String name;
    private String alias;
    private UserRole role;
    private Instant createDate;
    /**
     * account number where money to be transferred
     */
    private String bankAccountNumber;
    private Instant updateDate;
}
