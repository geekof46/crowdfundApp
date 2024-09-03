//package org.crowdfund.controller;
//
//import org.crowdfund.controller.DonationController;
//import org.crowdfund.models.DonationDTO;
//import org.crowdfund.models.DonationSaveDTO;
//import org.crowdfund.pojo.DonationSaveRequest;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class DonationControllerTest {
//
//    @Mock
//    private DonationService donationService;
//
//    @InjectMocks
//    private DonationController donationController;
//
//    @Test
//    public void testGetDonationByProjectId() {
//        // Arrange
//        String projectId = "test-project-id";
//        Integer pageSize = 10;
//        String next = "test-next";
//
//        // Act
//        ResponseEntity<PaginatedResultDTO<DonationDTO>> response = donationController.getDonationByProjectId(projectId, pageSize, next);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(donationService).getDonationsByProjectId(projectId, pageSize, next);
//    }
//
//    @Test
//    public void testAddDonation() {
//        // Arrange
//        DonationSaveRequest donationSaveRequest = new DonationSaveRequest();
//
//        // Act
//        ResponseEntity<Object> response = donationController.addDonation("test-project-id", donationSaveRequest);
//
//        // Assert
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        verify(donationService).saveDonation(any(DonationSaveDTO.class));
//    }
//
//    @Test
//    public void testGetDonationByDonorId() {
//        // Arrange
//        String donorId = "test-donor-id";
//        Integer pageSize = 10;
//        String next = "test-next";
//
//        // Act
//        ResponseEntity<PaginatedResultDTO<DonationDTO>> response = donationController.getDonationByDonorId(donorId, pageSize, next);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(donationService).getDonationsByDonorId(donorId, pageSize, next);
//    }
//
//    @Test
//    public void testBuildDonationSaveDTO() {
//        // Arrange
//        DonationSaveRequest donationSaveRequest = new DonationSaveRequest();
//
//        // Act
//        DonationSaveDTO donationSaveDTO = donationController.buildDonationSaveDTO(donationSaveRequest, "test-donor-id", "test-project-id");
//
//        // Assert
//        assertEquals("test-donor-id", donationSaveDTO.getDonorId());
//        assertEquals("test-project-id", donationSaveDTO.getProjectId());
//    }
//}
