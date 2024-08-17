package org.crowdfund.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;


@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbImmutable(builder = Customer.CustomerBuilder.class)
public class Customer {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute(value = "id")}))
    private String id;
    private String name;
    private String email;
    private Instant regDate;
}