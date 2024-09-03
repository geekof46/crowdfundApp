//package org.crowdfund.dao;
//
//import org.crowdfund.models.DonationDTO;
//import org.crowdfund.models.DonationSaveDTO;
//import org.crowdfund.models.PaginatedResultDTO;
//import org.crowdfund.models.db.Donation;
//import org.crowdfund.utils.ModelConvertor;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class DonationDaoTest {
//
//    @Mock
//    private DBAccessor<Donation> dynamoDBAccessor;
//
//    @Mock
//    private ModelConvertor modelConvertor;
//
//    @InjectMocks
//    private DonationDao donationDao;
//
//    @Test
//    public void testSave() {
//        // Arrange
//        DonationSaveDTO donationSaveDTO = new DonationSaveDTO();
//        Donation donation = new Donation();
//        when(modelConvertor.convert(donationSaveDTO, Donation.class)).thenReturn(donation);
//
//        // Act
//        donationDao.save(donationSaveDTO);
//
//        // Assert
//        verify(dynamoDBAccessor).putItem(donation, Donation.DONATION_ID, Donation.class);
//    }
//
//    @Test
//    public void testGetByProjectId() {
//        // Arrange
//        String projectId = "test-project-id";
//        Integer pageSize = 10;
//        String nextKey = "test-next-key";
//
//        // Act
//        PaginatedResultDTO<DonationDTO> result = donationDao.getByProjectId(projectId, pageSize, nextKey);
//
//        // Assert
//        assertEquals(new PaginatedResultDTO<>(), result);
//    }
//
//    @Test
//    public void testGetByDonorId() {
//        // Arrange
//        String donorId = "test-donor-id";
//        Integer pageSize = 10;
//        String nextKey = "test-next-key";
//
//        // Act
//        PaginatedResultDTO<DonationDTO> result = donationDao.getByDonorId(donorId, pageSize, nextKey);
//
//        // Assert
//        assertEquals(new PaginatedResultDTO<>(), result);
//    }
//}
