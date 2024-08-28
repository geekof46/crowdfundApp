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

    public List<DonationDTO> getDonationByProjectId(@NonNull String projectId) {
         return List.of();
    }

    public List<DonationDTO> getDonationByDonarId(@NonNull String donorId) {
        return List.of();
    }
}
