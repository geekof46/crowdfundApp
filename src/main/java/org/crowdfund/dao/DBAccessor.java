package org.crowdfund.dao;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.exceptions.NonRetryableException;
import org.crowdfund.exceptions.RecordNotFoundException;
import org.crowdfund.exceptions.RetryableDependencyException;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.IndexNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.InternalServerErrorException;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughputExceededException;
import software.amazon.awssdk.services.dynamodb.model.RequestLimitExceededException;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.TransactionCanceledException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DB accessor generic class
 *
 * @param <T>
 */
@Log4j2
public class DBAccessor<T> {

    //private static final Logger logger = LoggerFactory.getLogger(HandlerS3.class);
    public static final int BATCH_WRITE_LIMIT = 20;
    private final DynamoDbTable<T> dynamoDbTable;
    private final DynamoDbEnhancedClient dbEnhancedClient;

    public DBAccessor(@NonNull final DynamoDbEnhancedClient dbEnhancedClient,
                      @NonNull final DynamoDbTable<T> dynamoDbTable) {
        this.dynamoDbTable = dynamoDbTable;
        this.dbEnhancedClient = dbEnhancedClient;
    }

    /**
     * method to fetch DDB record by partition Key
     *
     * @param partitionKey
     * @param consistentRead - True/false
     * @return
     */
    public T getRecordByPartitionKey(@NonNull final String partitionKey,
                                     final boolean consistentRead) {

        List<T> quieriedRecords = new ArrayList<>();
        log.info("Fetching corresponding to partitionKey queryRecord: [{}]", partitionKey);
        try {
            final QueryConditional queryConditional = QueryConditional
                    .keyEqualTo(Key.builder()
                            .partitionValue(AttributeValue.builder().s(partitionKey).build())
                            .build());
            final QueryEnhancedRequest query = QueryEnhancedRequest.builder()
                    .queryConditional(queryConditional)
                    .consistentRead(consistentRead)
                    .build();
            dynamoDbTable.query(query).stream().forEach(page ->
                    quieriedRecords.addAll(page.items()));

            log.info("Resultant record: [{}]", quieriedRecords);
        } catch (final ProvisionedThroughputExceededException | RequestLimitExceededException |
                       InternalServerErrorException
                dependencyRetryableException) {
            final String errorMsg = String.format("Retryable exception occurred while querying DDB while querying for [%s].",
                    partitionKey);
            throw new RetryableDependencyException(errorMsg, dependencyRetryableException);
        } catch (final ResourceNotFoundException invalidRequestException) {
            final String errorMessage = String.format("DDB [%s] not found", dynamoDbTable.tableName());
            throw new InvalidRequestException(errorMessage, invalidRequestException);
        } catch (final SdkException sdkException) {
            handleSdkException(sdkException);
        }
        if(quieriedRecords.isEmpty()){
            throw new RecordNotFoundException("record not found for partition key "+ partitionKey);
        }
        return quieriedRecords.get(0);
    }

    /**
     *
     * @param indexName
     * @param partitionKeyColName
     * @param partitionKey
     * @param sortKeyColName
     * @param sortKey
     * @param limit
     * @return
     */
    public List<T> getRecordsByIndexPartitionKey(@NonNull final String indexName,
                                                 @NonNull final String partitionKeyColName,
                                                 @NonNull final String partitionKey,
                                                 @NonNull final  String sortKeyColName,
                                                 @NonNull final String sortKey,
                                                 final Integer limit) {
        return getRecordsByIndexPartitionKey(indexName,
                partitionKeyColName,
                partitionKey,
                sortKeyColName,
                sortKey,
                null,
                limit);
    }


    public List<T> getRecordsByIndexPartitionKey(@NonNull final String indexName,
                                                                     @NonNull final String partitionKeyColName,
                                                                     @NonNull final String partitionKey,
                                                                     @NonNull final  String sortKeyColName,
                                                                     @NonNull final String sortKey,
                                                                     final Expression filterExpression,
                                                 final Integer limit) {
        try {
            log.info("Fetching record by partition key {} of index {}", partitionKey, indexName);
            Map<String, AttributeValue> exclusiveStartKey = new HashMap<>();
            if (StringUtils.hasText(sortKey)
                    && StringUtils.hasText(partitionKey)) {
                exclusiveStartKey.put(partitionKeyColName, AttributeValue.builder()
                        .s(partitionKey)
                        .build());
                exclusiveStartKey.put(sortKeyColName, AttributeValue.builder()
                        .s(sortKey)
                        .build());
            } else {
                exclusiveStartKey = null;
            }
            final QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                    .partitionValue(partitionKey)
                    .build());
            final QueryEnhancedRequest.Builder queryReqBuilder = QueryEnhancedRequest.builder()
                    .queryConditional(queryConditional)
                    .exclusiveStartKey(exclusiveStartKey)
                    .scanIndexForward(true)
                    .limit(limit);

            if (filterExpression != null) {
                queryReqBuilder.filterExpression(filterExpression);
            }

            final SdkIterable<Page<T>> pages = dynamoDbTable.index(indexName)
                    .query(queryReqBuilder.build());
            final List<T> quieriedRecords = new ArrayList<>();

            pages.stream().limit(1).forEach(page -> {
                quieriedRecords.addAll(page.items());
            });

            log.info("Queried result {}", quieriedRecords);
            return quieriedRecords;
        } catch (final IndexNotFoundException e) {
            throw new NonRetryableException("Index not found in table" +
                    " to query using index " + indexName + " partition key", e);
        } catch (final DynamoDbException e) {
            throw new RetryableDependencyException("Failed to query table db record " +
                    "for partition key : " + partitionKey, e);
        }
    }

    /**
     * method to add record in DDB
     *
     * @param record
     */
    public void putItem(@NonNull final T record) {
        try {
            log.info("Put DB record request for record {}", record);
            dynamoDbTable.putItem(record);
            log.info("Put DB record request processed successfully");
        } catch (final TransactionCanceledException e) {
            throw new InvalidRequestException("failed to write record due to invalid request", e);
        } catch (final DynamoDbException e) {
            throw new RetryableDependencyException("Failed to write records", e);
        }
    }

    /**
     * Throws retryable or non-retryable dependency exception based on retryable attribute
     *
     * @param exception Exception thrown from DDB Client
     */
    private void handleSdkException(@NonNull final SdkException exception) {
        if (exception.retryable()) {
            throw new RetryableDependencyException(exception);
        }
        throw new RetryableDependencyException(exception);
    }
}

