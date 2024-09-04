package org.crowdfund.dao;


import lombok.NonNull;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.exceptions.ProjectNotFoundException;
import org.crowdfund.exceptions.RecordNotFoundException;
import org.crowdfund.models.PaginatedResultDTO;
import org.crowdfund.models.ProjectDTO;
import org.crowdfund.models.ProjectSaveDTO;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.models.db.PaginatedResult;
import org.crowdfund.models.db.Project;
import org.crowdfund.utils.AttributeValueUtil;
import org.crowdfund.utils.ModelConvertor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.utils.ImmutableMap;

import java.util.Map;

import static org.crowdfund.models.db.Project.INNOVATOR_ID;
import static org.crowdfund.models.db.Project.INNOVATOR_ID_INDEX;
import static org.crowdfund.models.db.Project.PROJECT_ID;
import static org.crowdfund.models.db.Project.PROJECT_STATUS;
import static org.crowdfund.models.db.Project.PROJECT_STATUS_INDEX;
import static org.crowdfund.utils.AttributeValueUtil.getNext;

/**
 * class for project DDB operations
 */
@Repository
public class ProjectDao {

    private final DBAccessor<Project> dynamoDBAccessor;
    private final ModelConvertor modelConvertor;

    public ProjectDao(@NonNull final DBAccessor<Project> dynamoDBAccessor,
                   @NonNull final ModelConvertor modelConvertor) {
        this.dynamoDBAccessor = dynamoDBAccessor;
        this.modelConvertor = modelConvertor;
    }

    /**
     * we want transactions save behaviour to save project while updating amount
     *
     * @param projectSaveDTO request object to create new Project record
     */
    public void save(@NonNull final ProjectSaveDTO projectSaveDTO) {
        dynamoDBAccessor.putItem(modelConvertor.convert(projectSaveDTO, Project.class),
                PROJECT_ID,
                Project.class);
    }

    /**
     * method to get project by projectId
     *
     * @param projectId unique identifier for project
     * @return Project matching with projectId
     */
    public ProjectDTO getProjectById(@NonNull final String projectId) {
        try {
            return modelConvertor.convert(
                    dynamoDBAccessor.getRecordByPartitionKey(projectId, true),
                    ProjectDTO.class);
        } catch (final RecordNotFoundException e) {
            throw new ProjectNotFoundException("Project not found with projectId " + projectId);
        }
    }

    public PaginatedResultDTO<ProjectDTO> getProjectsByInnovatorIdAndStatus(
            @NonNull final String innovatorId,
            @NonNull final ProjectStatus status,
            @NonNull final Integer pageSize,
            @NonNull final String nextKey) {

        final QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(innovatorId)
                .build());

        if (pageSize.compareTo(0) < 1) {
            throw new InvalidRequestException("Page Size can not be zero or negative");
        }

        final QueryEnhancedRequest.Builder queryReqBuilder = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .limit(pageSize)
                .scanIndexForward(true);

        Map<String, AttributeValue> lastEvaluatedKey = null;
        if (StringUtils.hasText(nextKey)) {
            String[] decrepatedKey = AttributeValueUtil.decryptLastEvaluatedKey(nextKey).split(",");
            queryReqBuilder.exclusiveStartKey(ImmutableMap.of(
                    PROJECT_ID, AttributeValue.builder().s(decrepatedKey[0]).build(),
                    INNOVATOR_ID, AttributeValue.builder().s(decrepatedKey[1]).build()
            ));
        }

        final Expression filterExpression = Expression.builder()
                .expression("#status = :status")
                .expressionNames(Map.of("#status", PROJECT_STATUS))
                .expressionValues(Map.of(":status", AttributeValue.builder()
                        .s(status.toString()).build()))
                .build();
        queryReqBuilder.filterExpression(filterExpression);

        final PaginatedResult<Project> result = dynamoDBAccessor.getRecordsByIndex(INNOVATOR_ID_INDEX,
                queryReqBuilder.build());

        return new PaginatedResultDTO<>(result.getRecords().stream()
                .map(record -> modelConvertor.convert(record, ProjectDTO.class))
                .toList(), getNext(result.getLastEvaluatedKey()));
    }


    /**
     * get all project status requested except project created by user who is donating
     *
     * @param innovatorId
     * @param status
     * @param pageSize
     * @param nextKey
     * @return
     */
    public PaginatedResultDTO<ProjectDTO> getProjectsByStatusForDonation(
            @NonNull final String innovatorId,
            @NonNull final ProjectStatus status,
            @NonNull final Integer pageSize,
            @NonNull final String nextKey) {

        final QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(status.name())
                .build());

        if (pageSize.compareTo(0) < 1) {
            throw new InvalidRequestException("Page Size can not be zero or negative");
        }

        final QueryEnhancedRequest.Builder queryReqBuilder = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .limit(pageSize)
                .scanIndexForward(true);

        if (StringUtils.hasText(nextKey)) {
            String[] decrepatedKey = AttributeValueUtil.decryptLastEvaluatedKey(nextKey).split(",");
            queryReqBuilder.exclusiveStartKey(ImmutableMap.of(
                    PROJECT_ID, AttributeValue.builder().s(decrepatedKey[0]).build(),
                    PROJECT_STATUS, AttributeValue.builder().s(decrepatedKey[1]).build()
            ));
        }

        final Expression filterExpression = Expression.builder()
                .expression("InnovatorId <> :innovatorId")
                .expressionValues(Map.of(":innovatorId", AttributeValue.builder()
                        .s(innovatorId).build()))
                .build();
        queryReqBuilder.filterExpression(filterExpression);

        final PaginatedResult<Project> result = dynamoDBAccessor.getRecordsByIndex(PROJECT_STATUS_INDEX,
                queryReqBuilder.build());

        return new PaginatedResultDTO<ProjectDTO>(result.getRecords().stream()
                .map(record -> modelConvertor.convert(record, ProjectDTO.class))
                .toList(), getNext(result.getLastEvaluatedKey()));
    }

    public void updateProject(@NonNull final ProjectDTO projectDTO) {
        dynamoDBAccessor.updateItem(modelConvertor.convert(projectDTO, Project.class),
                projectDTO.getVersion(), Project.class);
    }
}
