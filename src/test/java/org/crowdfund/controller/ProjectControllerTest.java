//package org.crowdfund.controller;
//
//import org.crowdfund.controller.ProjectController;
//import org.crowdfund.models.PaginatedResultDTO;
//import org.crowdfund.models.ProjectDTO;
//import org.crowdfund.models.ProjectSaveDTO;
//import org.crowdfund.pojo.ProjectSaveRequest;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ProjectControllerTest {
//
//    @Mock
//    private ProjectService projectService;
//
//    @InjectMocks
//    private ProjectController projectController;
//
//    @Test
//    public void testGetProjectsByStatus() {
//        // Arrange
//        String innovatorId = "test-innovator-id";
//        String status = "test-status";
//        Integer pageSize = 10;
//        String next = "test-next";
//
//        // Act
//        ResponseEntity<PaginatedResultDTO<ProjectDTO>> response = projectController.getProjectsByStatus(innovatorId, status, pageSize, next);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(projectService).getProjectsByInnovatorIdAndStatus(innovatorId, ProjectStatus.valueOf(status), pageSize, next);
//    }
//
//    @Test
//    public void testGetProjectsForDonation() {
//        // Arrange
//        String status = "test-status";
//        Integer pageSize = 10;
//        String next = "test-next";
//        String userId = "test-user-id";
//
//        // Act
//        ResponseEntity<PaginatedResultDTO<ProjectDTO>> response = projectController.getProjectsForDonation(status, pageSize, next, userId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(projectService).getProjectForDonation(userId, ProjectStatus.valueOf(status), pageSize, next);
//    }
//
//    @Test
//    public void testGetProjectById() {
//        // Arrange
//        String projectId = "test-project-id";
//
//        // Act
//        ResponseEntity<ProjectDTO> response = projectController.getProjectById(projectId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(projectService).getProjectById(projectId);
//    }
//
//    @Test
//    public void testAddProject() {
//        // Arrange
//        ProjectSaveRequest projectSaveRequest = new ProjectSaveRequest();
//
//        // Act
//        ResponseEntity<Object> response = projectController.addProject("test-innovator-id", projectSaveRequest);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(projectService).save(any(ProjectSaveDTO.class));
//    }
//
//    @Test
//    public void testBuildProjectSaveDTO() {
//        // Arrange
//        ProjectSaveRequest projectSaveRequest = new ProjectSaveRequest();
//
//        // Act
//        ProjectSaveDTO projectSaveDTO = projectController.buildProjectSaveDTO(projectSaveRequest);
//
//        // Assert
//        assertEquals("test-innovator-id", projectSaveDTO.getInnovatorId());
//        assertEquals("test-description", projectSaveDTO.getDescription());
//        assertEquals(ProjectStatus.REQUESTED, projectSaveDTO.getStatus());
//    }
//}
