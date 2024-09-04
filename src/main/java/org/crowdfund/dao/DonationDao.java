package org.crowdfund.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.crowdfund.models.DonationDTO;
import org.crowdfund.models.DonationSaveDTO;
import org.crowdfund.models.PaginatedResultDTO;
import org.crowdfund.models.db.Donation;
import org.crowdfund.models.db.PaginatedResult;
import org.crowdfund.models.db.Project;
import org.crowdfund.utils.AttributeValueUtil;
import org.crowdfund.utils.ModelConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.utils.ImmutableMap;

import java.util.Map;

import static org.crowdfund.models.db.Donation.DONATION_ID;
import static org.crowdfund.models.db.Donation.DONOR_ID;
import static org.crowdfund.models.db.Donation.DONOR_ID_INDEX;
import static org.crowdfund.models.db.Donation.PROJECT_ID;
import static org.crowdfund.models.db.Donation.PROJECT_ID_INDEX;
import static org.crowdfund.utils.AttributeValueUtil.getNext;

/**
 * dao layer class to do CURD on donation db
 */
@Repository
public class DonationDao {
    private final DBAccessor<Donation> dynamoDBAccessor;
    private final ModelConvertor modelConvertor;

    @Autowired
    public DonationDao(@NonNull final DynamoDbEnhancedClient dbEnhancedClient,
                       @NonNull final DynamoDbTable<Donation> dynamoDbTable,
                       @NonNull final ObjectMapper objectMapper) {
        this.dynamoDBAccessor = new DBAccessor<>(dbEnhancedClient, dynamoDbTable);
        this.modelConvertor = new ModelConvertor(objectMapper);
    }

    public DonationDao(@NonNull final DBAccessor<Donation> dynamoDBAccessor,
                      @NonNull final ModelConvertor modelConvertor) {
        this.dynamoDBAccessor = dynamoDBAccessor;
        this.modelConvertor = modelConvertor;
    }

    /**
     * method to create new donation record
     *
     * @param donationSaveDTO
     */
    public void save(@NonNull final DonationSaveDTO donationSaveDTO) {
        dynamoDBAccessor.putItem(modelConvertor.convert(donationSaveDTO, Donation.class),
                DONATION_ID,
                Donation.class);
    }

    public PaginatedResultDTO<DonationDTO> getByProjectId(@NonNull final String projectId,
                                                          @NonNull final Integer pageSize,
                                                          @NonNull final String nextKey) {

        Map<String, AttributeValue> lastEvaluatedKey = null;
        if (StringUtils.hasText(nextKey)) {
            final String[] decrepatedKey = AttributeValueUtil.decryptLastEvaluatedKey(nextKey).split(",");
            lastEvaluatedKey = ImmutableMap.of(
                    DONATION_ID, AttributeValue.builder().s(decrepatedKey[0]).build(),
                    PROJECT_ID, AttributeValue.builder().s(decrepatedKey[1]).build()
            );
        }
        final PaginatedResult<Donation> result = dynamoDBAccessor.getRecordByIndex(PROJECT_ID_INDEX,
                projectId, pageSize, lastEvaluatedKey);

        return new PaginatedResultDTO<DonationDTO>(result.getRecords().stream()
                .map(record -> modelConvertor.convert(record, DonationDTO.class))
                .toList(), getNext(result.getLastEvaluatedKey()));
    }

    public PaginatedResultDTO<DonationDTO> getByDonorId(@NonNull String donorId,
                                                        @NonNull final Integer pageSize,
                                                        @NonNull final String nextKey) {

        Map<String, AttributeValue> lastEvaluatedKey = null;
        if (StringUtils.hasText(nextKey)) {
            String[] decrepatedKey = AttributeValueUtil.decryptLastEvaluatedKey(nextKey).split(",");
            lastEvaluatedKey = ImmutableMap.of(
                    DONATION_ID, AttributeValue.builder().s(decrepatedKey[0]).build(),
                    DONOR_ID, AttributeValue.builder().s(decrepatedKey[1]).build()
            );
        }

        final PaginatedResult<Donation> result = dynamoDBAccessor.getRecordByIndex(DONOR_ID_INDEX,
                donorId, pageSize, lastEvaluatedKey);

        return new PaginatedResultDTO<DonationDTO>(result.getRecords().stream()
                .map(record -> modelConvertor.convert(record, DonationDTO.class))
                .toList(), getNext(result.getLastEvaluatedKey()));
    }
}


