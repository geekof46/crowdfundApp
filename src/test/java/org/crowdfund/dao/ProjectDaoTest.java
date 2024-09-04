package org.crowdfund.dao;

import org.crowdfund.models.PaginatedResultDTO;
import org.crowdfund.models.ProjectDTO;
import org.crowdfund.models.ProjectSaveDTO;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.models.db.Project;
import org.crowdfund.utils.ModelConvertor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import static org.crowdfund.TestUtils.*;
import static org.crowdfund.models.db.Project.INNOVATOR_ID_INDEX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectDaoTest {

    @Mock
    private DBAccessor<Project> dynamoDBAccessor;

    @Mock
    private ModelConvertor modelConvertor;

    @Captor
    private ArgumentCaptor<QueryEnhancedRequest> QueryEnhancedRequestCaptor;

    private ProjectDao projectDao;

    @BeforeEach
    public void setUp() {
        projectDao = new ProjectDao(dynamoDBAccessor, modelConvertor);
    }

    @Test
    public void testSave() {
        // Arrange
        ProjectSaveDTO projectSaveDTO = getProjectSaveDTO();
        Project project = getProject();
        when(modelConvertor.convert(projectSaveDTO, Project.class)).thenReturn(project);

        // Act
        projectDao.save(projectSaveDTO);

        // Assert
        verify(dynamoDBAccessor).putItem(project, Project.PROJECT_ID, Project.class);
    }

    @Test
    public void testGetProjectById() {
        // Arrange
        final String projectId = "test-project-id";
        final Project project = getProject();
        when(dynamoDBAccessor.getRecordByPartitionKey(projectId, true)).thenReturn(project);
        when(modelConvertor.convert(project, ProjectDTO.class)).thenReturn(getProjectDTO());

        // Act
        final ProjectDTO result = projectDao.getProjectById(projectId);

        // Assert
        assertEquals(projectId, result.getProjectId());
    }

    @Test
    public void testGetProjectsByInnovatorIdAndStatus() {
        // Arrange
        String innovatorId = "test-innovator-id";
        ProjectStatus status = ProjectStatus.REQUESTED;
        Integer pageSize = 10;
        String nextKey = "";


         when(dynamoDBAccessor.getRecordsByIndex(eq(INNOVATOR_ID_INDEX),
                 QueryEnhancedRequestCaptor.capture())).thenReturn(getPaginatedResult());

        // Act
        PaginatedResultDTO<ProjectDTO> result = projectDao.getProjectsByInnovatorIdAndStatus(innovatorId, status,
                pageSize, nextKey);

        // Assert
        final PaginatedResultDTO<ProjectDTO> expected = getPaginatedDTOResult();
        //TODO assert reult
        //TODO assert
        //QueryEnhancedRequestCaptor.getValue()
        // TODO compare result with getPaginatedResult()
    }

//    @Test
//    public void testUpdateProject() {
//        // Arrange
//        final ProjectDTO projectDTO = getProjectDTO();
//
//        // Act
//        projectDao.updateProject(projectDTO);
//
//        // Assert
//        verify(dynamoDBAccessor).updateItem(any(Project.class), any(Integer.class), eq(Project.class));
//    }
}
