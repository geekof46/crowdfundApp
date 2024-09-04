package org.crowdfund.controller;

import org.crowdfund.models.PaginatedResultDTO;
import org.crowdfund.models.ProjectDTO;
import org.crowdfund.models.ProjectSaveDTO;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.pojo.ProjectSaveRequest;
import org.crowdfund.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.crowdfund.TestUtils.getProjectDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Test
    public void testGetProjectById() {
        // Arrange
        final String projectId = "test-project-id";
        final ProjectDTO projectDTO = getProjectDTO();
        when(projectService.getProjectById(projectId)).thenReturn(projectDTO);

        // Act
        ResponseEntity<ProjectDTO> response = projectController.getProjectById(projectId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(projectService).getProjectById(projectId);
        assertEquals(projectId, projectDTO.getProjectId());
    }

    @Test
    public void testAddProject() {
        // Arrange
        ProjectSaveRequest projectSaveRequest = getProjectSaveRequest();

        // Act
        ResponseEntity<Object> response = projectController.addProject("innovatorId",
                projectSaveRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(projectService).save(any(ProjectSaveDTO.class));
    }

    @Test
    public void testBuildProjectSaveDTO() {
        // Arrange
        final ProjectSaveRequest projectSaveRequest = getProjectSaveRequest();

        // Act
        final ProjectSaveDTO projectSaveDTO = projectController.buildProjectSaveDTO(projectSaveRequest);

        // Assert
        assertEquals("innovatorId", projectSaveDTO.getInnovatorId());
        assertEquals("description", projectSaveDTO.getDescription());
        assertEquals(ProjectStatus.REQUESTED, projectSaveDTO.getStatus());
    }

    private ProjectSaveRequest getProjectSaveRequest() {
        return ProjectSaveRequest.builder()
                .innovatorId("innovatorId")
                .description("description")
                .requestedAmount(BigDecimal.TEN)
                .build();
    }

    //TODO pending

    //    @Test
//    public void testGetProjectsByStatus() {
//        // Arrange
//        String innovatorId = "test-innovator-id";
//        String status = ProjectStatus.REQUESTED.name();
//        Integer pageSize = 10;
//        String next = "test-next";
//
//        when(projectService.getProjectById(innovatorId, ProjectStatus.valueOf(status), pageSize, next))
//                .thenReturn(getProjectDTO());
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
//        String status = ProjectStatus.REQUESTED.name();
//        Integer pageSize = 10;
//        String next = "test-next";
//        String userId = "test-user-id";
//
//        // Act
//        ResponseEntity<PaginatedResultDTO<ProjectDTO>> response = projectController
//                .getProjectsForDonation(status, pageSize, next, userId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(projectService).getProjectForDonation(userId, ProjectStatus.valueOf(status), pageSize, next);
//    }

}
