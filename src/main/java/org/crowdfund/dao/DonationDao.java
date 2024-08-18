package org.crowdfund.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.crowdfund.models.DonationDTO;
import org.crowdfund.models.db.Donation;
import org.crowdfund.utils.ModelConvertor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * dao layer class to to do CURD on donation db
 */
@Repository
public class DonationDao {
    private final DBAccessor<Donation> dynamoDBAccessor;
    private final ModelConvertor modelConvertor;

    public DonationDao(@NonNull final DynamoDbEnhancedClient dbEnhancedClient,
                       @NonNull final DynamoDbTable<Donation> dynamoDbTable,
                       @NonNull final ObjectMapper objectMapper) {
        this.dynamoDBAccessor = new DBAccessor<>(dbEnhancedClient, dynamoDbTable);
        this.modelConvertor = new ModelConvertor(objectMapper);
    }

    /**
     * method to create new donation record
     * @param donationDTO
     */
   public void save(@NonNull final DonationDTO donationDTO){
       dynamoDBAccessor.putItem(modelConvertor.convert(donationDTO, Donation.class));
   }


    /**
     * method to get all donations done by donarId
     *  <TODO add pagination support></>
     * @param donarId
     * @return
     */
   public List<DonationDTO> getDonationByDonarId(@NonNull final String donarId){
       return dynamoDBAccessor.getRecordsByIndexPartitionKey(Donation.DONOR_ID_TO_DONATION_ID_INDEX,
               donarId).stream().map(record ->
               modelConvertor.convert(record,DonationDTO.class)).collect(Collectors.toList());
   }

    /**
     * method to get all donations done on Project
     * <TODO add pagination support></>
     * @param projectId
     * @return list of donations
     */
    public List<DonationDTO> getDonationByProjectId(@NonNull final String projectId){
       return dynamoDBAccessor.getRecordsByIndexPartitionKey(Donation.PROJECT_ID_TO_DONATION_ID_INDEX,
               projectId).stream().map(record ->
               modelConvertor.convert(record,DonationDTO.class)).collect(Collectors.toList());
    }
}
