package org.crowdfund.dao;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.crowdfund.exceptions.ProjectNotFoundException;
import org.crowdfund.exceptions.RecordNotFoundException;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.models.db.Project;
import org.crowdfund.pojo.ProjectDTO;
import org.crowdfund.utils.ModelConvertor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

/**
 * class for project DDB operations
 */
@Repository
public class ProjectDao {

    private final DBAccessor<Project> dynamoDBAccessor;
    private final ModelConvertor modelConvertor;

    public ProjectDao(@NonNull final DynamoDbEnhancedClient dbEnhancedClient,
                      @NonNull final DynamoDbTable<Project> dynamoDbTable,
                      @NonNull final ObjectMapper objectMapper) {
        this.dynamoDBAccessor = new DBAccessor<>(dbEnhancedClient, dynamoDbTable);
        this.modelConvertor = new ModelConvertor(objectMapper);
    }

    /**
     * we want transactions save behaviour to save project while updating amount
     *
     * @param projectDTO
     */
    public void save(@NonNull final ProjectDTO projectDTO) {
        dynamoDBAccessor.putItem(modelConvertor.convert(projectDTO, Project.class));
    }

    /**
     * method to get project by projectId
     * @param projectId unique identifier for project
     * @return Project matching with projectId
     */
    public ProjectDTO getProjectById(@NonNull final String projectId) {
        try {
            return  modelConvertor.convert(
                    dynamoDBAccessor.getRecordByPartitionKey(projectId, true),
                    ProjectDTO.class);
        } catch (final RecordNotFoundException e) {
            throw new ProjectNotFoundException("Project not found with  projectId " + projectId);
        }
    }

    /**
     * method to get paginated result for project with status
     * @param projectStatus
     * @param limit
     * @param next
     * @return
     */
    public List<ProjectDTO> getProjectsByStatus(
            @NonNull final ProjectStatus projectStatus,
            @NonNull final Integer limit,
            @NonNull final String next) {

        final List<Project> records = dynamoDBAccessor.getRecordsByIndexPartitionKey(
                Project.PROJECT_STATUS_TO_PROJECT_ID_INDEX,
                Project.PROJECT_STATUS,
                projectStatus.name(),
                Project.PROJECT_ID,
                next,
                limit);

        return records.stream()
                .map(record -> modelConvertor.convert(record, ProjectDTO.class))
                .toList();
    }

    public List<ProjectDTO> getProjectsByInnovator(
            @NonNull final String  innovatorId,
            @NonNull final Integer limit,
            @NonNull final String next) {

        final List<Project> records = dynamoDBAccessor.getRecordsByIndexPartitionKey(
                Project.INNOVATOR_ID_TO_PROJECT_ID_INDEX,
                Project.INNOVATOR_ID,
                innovatorId,
                Project.PROJECT_ID,
                next,
                limit);

        return records.stream()
                .map(record -> modelConvertor.convert(record, ProjectDTO.class))
                .toList();
    }

    public List<ProjectDTO> getProjectsByInnovatorIdAndStatus(
            @NonNull final String  innovatorId,
            @NonNull final ProjectStatus status,
            @NonNull final Integer limit,
            @NonNull final String next) {


        final Expression filterExpression = Expression.builder()
                .expression("#status = :status")
                .expressionNames(Map.of("#status","status"))
                .expressionValues(Map.of(":status", AttributeValue.builder()
                        .s(status.toString()).build()))
                .build();
        final List<Project> records = dynamoDBAccessor.getRecordsByIndexPartitionKey(
                Project.INNOVATOR_ID_TO_PROJECT_ID_INDEX,
                Project.INNOVATOR_ID,
                innovatorId,
                Project.PROJECT_ID,
                next,
                filterExpression,
                limit);

        return records.stream()
                .map(record -> modelConvertor.convert(record, ProjectDTO.class))
                .toList();
    }
}
