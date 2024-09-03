//package org.crowdfund.dao;
//
//import org.crowdfund.models.PaginatedResultDTO;
//import org.crowdfund.models.ProjectDTO;
//import org.crowdfund.models.ProjectSaveDTO;
//import org.crowdfund.models.ProjectStatus;
//import org.crowdfund.models.db.Project;
//import org.crowdfund.utils.ModelConvertor;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ProjectDaoTest {
//
//    @Mock
//    private DBAccessor<Project> dynamoDBAccessor;
//
//    @Mock
//    private ModelConvertor modelConvertor;
//
//    @InjectMocks
//    private ProjectDao projectDao;
//
//    @Test
//    public void testSave() {
//        // Arrange
//        ProjectSaveDTO projectSaveDTO = new ProjectSaveDTO();
//        Project project = new Project();
//        when(modelConvertor.convert(projectSaveDTO, Project.class)).thenReturn(project);
//
//        // Act
//        projectDao.save(projectSaveDTO);
//
//        // Assert
//        verify(dynamoDBAccessor).putItem(project, Project.PROJECT_ID, Project.class);
//    }
//
//    @Test
//    public void testGetProjectById() {
//        // Arrange
//        String projectId = "test-project-id";
//        Project project = new Project();
//        when(dynamoDBAccessor.getRecordByPartitionKey(projectId, true)).thenReturn(project);
//        when(modelConvertor.convert(project, ProjectDTO.class)).thenReturn(new ProjectDTO());
//
//        // Act
//        ProjectDTO result = projectDao.getProjectById(projectId);
//
//        // Assert
//        assertEquals(new ProjectDTO(), result);
//    }
//
//    @Test
//    public void testGetProjectsByInnovatorIdAndStatus() {
//        // Arrange
//        String innovatorId = "test-innovator-id";
//        ProjectStatus status = ProjectStatus.REQUESTED;
//        Integer pageSize = 10;
//        String nextKey = "test-next-key";
//
//        // Act
//        PaginatedResultDTO<ProjectDTO> result = projectDao.getProjectsByInnovatorIdAndStatus(innovatorId, status, pageSize, nextKey);
//
//        // Assert
//        assertEquals(new PaginatedResultDTO<>(), result);
//    }
//
//    @Test
//    public void testUpdateProject() {
//        // Arrange
//        ProjectDTO projectDTO = new ProjectDTO();
//
//        // Act
//        projectDao.updateProject(projectDTO);
//
//        // Assert
//        verify(dynamoDBAccessor).updateItem(any(Project.class), any(Integer.class), eq(Project.class));
//    }
//}
