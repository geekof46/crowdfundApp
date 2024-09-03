package org.crowdfund.models.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.crowdfund.models.ProjectCategory;
import org.crowdfund.models.ProjectStatus;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbAutoGeneratedTimestampAttribute;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbVersionAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.UpdateBehavior;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbUpdateBehavior;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * model class for project created by innovator
 */
@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbImmutable(builder = Project.ProjectBuilder.class)
public class Project {
    public static final String TABLE_NAME = "Projects";
    public static final String PROJECT_ID = "ProjectId";
    public static final String INNOVATOR_ID = "InnovatorId";
    public static final String PROJECT_STATUS = "Status";
    public static final String INNOVATOR_ID_INDEX = "InnovatorId-Index";
    public static final String PROJECT_STATUS_INDEX = "Status-Index";

    @Getter(onMethod = @__({@DynamoDbAttribute("ProjectId"),
            @DynamoDbPartitionKey}))
    private String projectId;

    @Getter(onMethod = @__({@DynamoDbAttribute("thumbnailLink")}))
    private String thumbnailLink;

    @Getter(onMethod = @__({@DynamoDbAttribute("Description")}))
    private String description;

    @Getter(onMethod = @__({@DynamoDbAttribute("Name")}))
    private String name;

    @Getter(onMethod = @__({@DynamoDbAttribute("Category")}))
    private ProjectCategory category;

    @Getter(onMethod = @__({@DynamoDbAttribute("SubCategory")}))
    private String subCategory;
    /**
     * mapped with userId from Users table
     */
    @Getter(onMethod = @__({@DynamoDbAttribute("InnovatorId"),
            @DynamoDbSecondaryPartitionKey(
                    indexNames = {INNOVATOR_ID_INDEX})}))
    private String innovatorId;

    @Getter(onMethod = @__({@DynamoDbAttribute("RequestedAmount")}))
    private BigDecimal requestedAmount;
    /**
     * total amount collected after donation
     */
    @Getter(onMethod = @__({@DynamoDbAttribute("ReceivedDonationAmount")}))
    private BigDecimal receivedDonationAmount;

    @Getter(onMethod = @__({@DynamoDbAttribute(PROJECT_STATUS),
            @DynamoDbSecondaryPartitionKey(
                    indexNames = {PROJECT_STATUS_INDEX})}))
    private ProjectStatus status;
    /**
     * to manager concurrency of donation events
     * dynamodb conditional writes are transactional in nature
     */
    @Getter(onMethod = @__({@DynamoDbAttribute("Version"),
            @DynamoDbVersionAttribute}))
    private Integer version;

    /**
     * Duration of donation for project will be
     * between DonationStartDate & donationEndDate
     */
    @Getter(onMethod = @__({
            @DynamoDbAttribute("DonationStartDate"),
            @DynamoDbUpdateBehavior(UpdateBehavior.WRITE_IF_NOT_EXISTS)}))
    private Instant donationStartDate;

    @Getter(onMethod = @__({@DynamoDbAttribute("DonationDate")}))
    private Instant donationEndDate;

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