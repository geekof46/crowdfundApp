//package org.crowdfund.service;
//
//import org.crowdfund.dao.ProjectDao;
//import org.crowdfund.exceptions.InvalidRequestException;
//import org.crowdfund.models.PaginatedResultDTO;
//import org.crowdfund.models.ProjectDTO;
//import org.crowdfund.models.ProjectSaveDTO;
//import org.crowdfund.models.ProjectStatus;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//public class ProjectServiceTest {
//
//    @Mock
//    private ProjectDao projectDao;
//
//    @InjectMocks
//    private ProjectService projectService;
//
//    @Test
//    public void testSave() {
//        // Arrange
//        ProjectSaveDTO projectSaveDTO = getProjectSaveDTO();
//
//        // Act
//        projectService.save(projectSaveDTO);
//
//        // Assert
//        verify(projectDao).save(projectSaveDTO);
//    }
//
//    @Test
//    public void testGetProjectsByInnovatorIdAndStatus() {
//        // Arrange
//        String innovatorId = "test-innovator-id";
//        ProjectStatus status = ProjectStatus.REQUESTED;
//        Integer limit = 10;
//        String next = "test-next";
//
//        // Act
//        PaginatedResultDTO<ProjectDTO> result =
//                projectService.getProjectsByInnovatorIdAndStatus(innovatorId, status, limit, next);
//
//        // Assert
//        assertEquals(new PaginatedResultDTO<>(), result);
//    }
//
//    @Test
//    public void testGetProjectById() {
//        // Arrange
//        String projectId = "test-project-id";
//
//        // Act
//        ProjectDTO result = projectService.getProjectById(projectId);
//
//        // Assert
//        assertEquals(new ProjectDTO(), result);
//    }
//
//    @Test
//    public void testUpdateProjectForDonationAmount() {
//        // Arrange
//        String projectId = "test-project-id";
//        BigDecimal donationAmount = BigDecimal.TEN;
//
//        // Act
//        projectService.updateProjectForDonationAmount(projectId, donationAmount);
//
//        // Assert
//        verify(projectDao).updateProject(any(ProjectDTO.class));
//    }
//
//    @Test
//    public void testGetProjectForDonation() {
//        // Arrange
//        String userId = "test-user-id";
//        ProjectStatus projectStatus = ProjectStatus.REQUESTED;
//        Integer pageSize = 10;
//        String next = "test-next";
//
//        // Act
//        PaginatedResultDTO<ProjectDTO> result = projectService.getProjectForDonation(userId, projectStatus, pageSize, next);
//
//        // Assert
//        assertEquals(new PaginatedResultDTO<>(), result);
//    }
//
//    @Test
//    public void testUpdateProjectForDonationAmount_InvalidRequestException() {
//        // Arrange
//        String projectId = "test-project-id";
//        BigDecimal donationAmount = BigDecimal.TEN;
//
//        // Act and Assert
//        assertThrows(InvalidRequestException.class, () -> projectService.updateProjectForDonationAmount(projectId, donationAmount));
//    }
//
//    private ProjectSaveDTO getProjectSaveDTO() {
//        return ProjectSaveDTO.builder()
//                .build();
//    }
//
//    private ProjectDTO getProjectDTO() {
//        return ProjectDTO.builder()
//                .projectId("test-project-id")
//                .innovatorId("test-innovator-id")
//                .status(ProjectStatus.REQUESTED)
//                .build();
//    }
//
//    private PaginatedResultDTO<ProjectDTO> getPaginatedDTOResult() {
//        return PaginatedResultDTO.<ProjectDTO>builder()
//                .records(List.of(getProjectDTO()))
//                .next("")
//                .build();
//    }
//}
