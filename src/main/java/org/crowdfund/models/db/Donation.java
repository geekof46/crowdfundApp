package org.crowdfund.models.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;

import java.math.BigDecimal;
import java.time.Instant;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbImmutable(builder = Donation.DonationBuilder.class)
public class Donation {

    public static final String PROJECT_ID_TO_DONATION_ID_INDEX =
            "projectId-to-donationId-index";
    public static final String DONOR_ID_TO_DONATION_ID_INDEX =
            "donarId-to-donationId-index";
    /**
     * //TODO add indexes
     * projectId -> donationId
     * donorId -> donationId
     */
    @Getter(onMethod = @__({@DynamoDbPartitionKey,
            @DynamoDbSecondarySortKey(indexNames =
                    {PROJECT_ID_TO_DONATION_ID_INDEX,
                    DONOR_ID_TO_DONATION_ID_INDEX})}))
    private String donationId;
    @Getter(onMethod = @__({
            @DynamoDbSecondaryPartitionKey(indexNames = {DONOR_ID_TO_DONATION_ID_INDEX})}))
    private String donorId;

    @Getter(onMethod = @__({
            @DynamoDbSecondaryPartitionKey(indexNames = {PROJECT_ID_TO_DONATION_ID_INDEX})}))
    private String projectId;
    private BigDecimal donationAmount;
    private String comment;
    private Instant donationDate;
}
