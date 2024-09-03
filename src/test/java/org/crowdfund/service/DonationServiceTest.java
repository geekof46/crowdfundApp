//package org.crowdfund.service;
//
//import org.crowdfund.dao.DonationDao;
//import org.crowdfund.models.DonationDTO;
//import org.crowdfund.models.DonationSaveDTO;
//import org.crowdfund.models.PaginatedResultDTO;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//public class DonationServiceTest {
//
//    @Mock
//    private DonationDao donationDao;
//
//    @Mock
//    private ProjectService projectService;
//
//    @InjectMocks
//    private DonationService donationService;
//
//    @Test
//    public void testSaveDonation() {
//        // Arrange
//        DonationSaveDTO donationSaveDTO = new DonationSaveDTO();
//
//        // Act
//        donationService.saveDonation(donationSaveDTO);
//
//        // Assert
//        verify(projectService).updateProjectForDonationAmount(any(String.class), any(Double.class));
//        verify(donationDao).save(donationSaveDTO);
//    }
//
//    @Test
//    public void testGetDonationsByProjectId() {
//        // Arrange
//        String projectId = "test-project-id";
//        Integer projectSize = 10;
//        String next = "test-next";
//
//        // Act
//        PaginatedResultDTO<DonationDTO> result = donationService.getDonationsByProjectId(projectId, projectSize, next);
//
//        // Assert
//        assertEquals(new PaginatedResultDTO<>(), result);
//    }
//
//    @Test
//    public void testGetDonationsByDonorId() {
//        // Arrange
//        String donorId = "test-donor-id";
//        Integer pageSize = 10;
//        String next = "test-next";
//
//        // Act
//        PaginatedResultDTO<DonationDTO> result = donationService.getDonationsByDonorId(donorId, pageSize, next);
//
//        // Assert
//        assertEquals(new PaginatedResultDTO<>(), result);
//    }
//}
