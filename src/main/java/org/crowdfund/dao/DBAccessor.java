package org.crowdfund.dao;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.exceptions.NonRetryableException;
import org.crowdfund.exceptions.RetryableDependencyException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.IndexNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.InternalServerErrorException;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughputExceededException;
import software.amazon.awssdk.services.dynamodb.model.RequestLimitExceededException;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.TransactionCanceledException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DBAccessor<T> {

    public static final int BATCH_WRITE_LIMIT = 20;
    private final DynamoDbTable<T> dynamoDbTable;
    private final DynamoDbEnhancedClient dbEnhancedClient;

    public DBAccessor(@NonNull final DynamoDbEnhancedClient dbEnhancedClient,
                      @NonNull final DynamoDbTable<T> dynamoDbTable) {
        this.dynamoDbTable = dynamoDbTable;
        this.dbEnhancedClient = dbEnhancedClient;
    }

    /**
     *
     * @param partitionKey
     * @return
     */
    public T getRecordsByPartitionKey(@NonNull final String partitionKey){
        T record = null;
        log.info("Fetching corresponding to the partitionKey: [{}]", partitionKey);
        try {
            record = dynamoDbTable.getItem(Key.builder().partitionValue(partitionKey).build());
            log.info("Resultant record: [{}]", record);
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

        return record;
    }

    public List<T> getRecordsByIndexPartitionKey(@NonNull final String indexName,
                                                 @NonNull final String partitionKey){
        try {
            final QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                    .partitionValue(partitionKey)
                    .build());
            final QueryEnhancedRequest query = QueryEnhancedRequest.builder()
                    .queryConditional(queryConditional)
                    .consistentRead(false)
                    .build();
            final List<T> quieriedRecords = new ArrayList<>();
            dynamoDbTable.index(indexName)
                    .query(query).stream().forEach(page ->
                            quieriedRecords.addAll(page.items()));

            return quieriedRecords;
        } catch (final IndexNotFoundException e){
            throw new NonRetryableException("Index not found in table" +
                    " to query using index " + indexName + " partition key", e);
        } catch (final DynamoDbException e){
            throw new RetryableDependencyException("Failed to query table db record " +
                    "for partition key : " + partitionKey, e);
        }
    }

    /**
     *
     * @param record
     */
    public void putItem(@NonNull final T record) {
        try {
            dynamoDbTable.putItem(record);
        } catch (final TransactionCanceledException e){
            throw new InvalidRequestException("failed to write record due to invalid request", e);
        } catch (final DynamoDbException e){
            throw new RetryableDependencyException("Failed to write records", e);
        }
    }

    /**
     * Throws retryable or non-retryable dependency exception based on retryable attribute
     * @param exception Exception thrown from DDB Client
     */
    private void handleSdkException(@NonNull final SdkException exception) {
        if (exception.retryable()) {
            throw new RetryableDependencyException(exception);
        }
        throw new RetryableDependencyException(exception);
    }
}

