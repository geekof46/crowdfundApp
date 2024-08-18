package org.crowdfund.models.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.crowdfund.models.ProjectStatus;
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
@DynamoDbImmutable(builder = Project.ProjectBuilder.class)
public class Project {
    public static final String TABLE_NAME = "Projects";
    public static final String INNOVATOR_ID_TO_PROJECT_ID_INDEX =
            "innovatorId-to-projectId-index";
    public static final String PROJECT_STATUS_TO_PROJECT_ID_INDEX =
            "projectstatus-to-projectId-index";

    @Getter(onMethod = @__({@DynamoDbPartitionKey,
            @DynamoDbSecondarySortKey(
                    indexNames = {INNOVATOR_ID_TO_PROJECT_ID_INDEX,
                            PROJECT_STATUS_TO_PROJECT_ID_INDEX})}))
    private String projectId;
    private String thumbnailLink;
    private String description;
    /**
     * mapped with userId from Users table
     */
    @Getter(onMethod = @__({@DynamoDbSecondaryPartitionKey(
                    indexNames = {INNOVATOR_ID_TO_PROJECT_ID_INDEX,})}))
    private String innovatorId;
    private BigDecimal expectAmount;
    /**
     * total amount collected after donation
     */
    private BigDecimal receivedAmount;
    private Instant donationDate;
    @Getter(onMethod = @__({@DynamoDbSecondaryPartitionKey(
            indexNames = {PROJECT_STATUS_TO_PROJECT_ID_INDEX,})}))
    private ProjectStatus status;
    /**
     * to manager concurrency of donation events
     * dynamodb conditional writes are transactional in nature
     */
    private Integer version;
    private Instant createDate;
    private Instant updateDate;
}