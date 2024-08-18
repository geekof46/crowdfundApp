package org.crowdfund.dao;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.models.db.Project;
import org.crowdfund.pojo.ProjectDTO;
import org.crowdfund.utils.ModelConvertor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.List;
import java.util.stream.Collectors;

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
     * @param projectDTO
     */
    public void save(@NonNull final ProjectDTO projectDTO){
        dynamoDBAccessor.putItem(modelConvertor.convert(projectDTO, Project.class));
    }

    public ProjectDTO getProjectById(@NonNull final String projectId){
        return modelConvertor.convert(dynamoDBAccessor.getRecordsByPartitionKey(projectId), ProjectDTO.class);
    }

    /**
     *
     * @param projectStatus
     * @return
     */
    public List<ProjectDTO> getProjectByStatus(@NonNull final ProjectStatus projectStatus){
        return dynamoDBAccessor.getRecordsByIndexPartitionKey(Project.PROJECT_STATUS_TO_PROJECT_ID_INDEX,
                projectStatus.toString()).stream().map(record ->
                modelConvertor.convert(record, ProjectDTO.class)).collect(Collectors.toList());
    }

    /**
     *
     * @param innovatorId
     * @return
     */
    public List<ProjectDTO> getProjectByInnovatorId(@NonNull final String innovatorId){
        return dynamoDBAccessor.getRecordsByIndexPartitionKey(Project.INNOVATOR_ID_TO_PROJECT_ID_INDEX,
                innovatorId).stream().map(record ->
                modelConvertor.convert(record, ProjectDTO.class)).collect(Collectors.toList());
    }
}
