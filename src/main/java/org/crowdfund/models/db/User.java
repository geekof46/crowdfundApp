package org.crowdfund.models.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbAutoGeneratedTimestampAttribute;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbVersionAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.UpdateBehavior;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbUpdateBehavior;

import java.time.Instant;

/**
 * class for dynamodb user model
 */
@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbImmutable(builder = User.UserBuilder.class)
public class User {
    /**
     * The constant TABLE_NAME.
     */
    public static final String TABLE_NAME = "Users";
    /**
     * The constant USER_ID.
     */
    public static final String USER_ID = "UserId";
    /**
     * The constant EMAIL_ID.
     */
    public static final String EMAIL_ID = "EmailId";

    @Getter(onMethod = @__({@DynamoDbAttribute(USER_ID),
            @DynamoDbPartitionKey}))
    private String userId;

    @Getter(onMethod = @__({@DynamoDbAttribute(EMAIL_ID)}))
    private String emailId;

    @Getter(onMethod = @__({@DynamoDbAttribute("Name")}))
    private String name;

    @Getter(onMethod = @__({@DynamoDbAttribute("PhoneNumber")}))
    private String phoneNumber;
    /**
     * TODO add bank details in such way that it can support different options
     * to send payment to Innovator account
     * account number where money to be transferred
     */
    @Getter(onMethod = @__({@DynamoDbAttribute("BankAccountNumber")}))
    private String bankAccountNumber;

    /**
     * dynamodb version attribute
     */
    @Getter(onMethod = @__({@DynamoDbAttribute("Version"),
            @DynamoDbVersionAttribute}))
    private Integer version;

    @Getter(onMethod = @__({
            @DynamoDbAttribute("CreateDate"),
            @DynamoDbAutoGeneratedTimestampAttribute,
            @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_IF_NOT_EXISTS)}))
    private Instant createDate;

    @Getter(onMethod = @__({@DynamoDbAutoGeneratedTimestampAttribute,
            @DynamoDbAttribute("UpdateDate"),
            @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_ALWAYS)}))
    private Instant updateDate;
}
