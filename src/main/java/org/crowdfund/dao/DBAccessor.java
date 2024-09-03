package org.crowdfund.dao;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.exceptions.NonRetryableException;
import org.crowdfund.exceptions.RecordNotFoundException;
import org.crowdfund.exceptions.RetryableDependencyException;
import org.crowdfund.exceptions.RetryableException;
import org.crowdfund.models.db.PaginatedResult;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.IndexNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.InternalServerErrorException;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughputExceededException;
import software.amazon.awssdk.services.dynamodb.model.RequestLimitExceededException;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.TransactionCanceledException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Dynamodb accessor generic class
 *
 * @param <T>
 */
@Log4j2
public class DBAccessor<T> {

    //private static final Logger logger = LoggerFactory.getLogger(HandlerS3.class);
    public static final int BATCH_WRITE_LIMIT = 20;
    private final DynamoDbTable<T> dynamoDbTable;
    private final DynamoDbEnhancedClient dbEnhancedClient;
    //private static GSON gson = GSON.

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

        log.info("Fetching corresponding to partitionKey queryRecord: [{}]", partitionKey);
        List<T> quieriedRecords = new ArrayList<>();

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

            log.info("Response of DB query for partitionKey {} record: [{}]", partitionKey, quieriedRecords);
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
        if (quieriedRecords.isEmpty()) {
            throw new RecordNotFoundException("record not found for partition key " + partitionKey);
        }
        return quieriedRecords.get(0);
    }

    /**
     * method to create/update record in DDB
     *
     * @param record
     */
    public void putItem(@NonNull final T record,
                        @NonNull final String partitionKeyName,
                        @NonNull final Class<T> className) {
        try {
            log.info("Put DB record request for record {}", record);
            final PutItemEnhancedRequest<T> putItemEnhancedRequest = PutItemEnhancedRequest.builder(className)
                    .item(record)
                    .conditionExpression(Expression.builder()
                            .expression("attribute_not_exists(" + partitionKeyName + ")")
                            .build())
                    .build();
            dynamoDbTable.putItem(putItemEnhancedRequest);
            log.info("Put DB record request processed successfully");
        } catch (final ConditionalCheckFailedException e) {
            throw new InvalidRequestException("Record already exist", e);
        } catch (final TransactionCanceledException e) {
            throw new InvalidRequestException("failed to write record due to invalid request", e);
        } catch (final DynamoDbException e) {
            throw new RetryableDependencyException("Failed to write records", e);
        }
    }

    /**
     * method to update record
     *
     * @param record      updated record
     * @param prevVersion name of partitionKey column
     * @param className   class name for DDB table
     */
    public void updateItem(@NonNull final T record,
                           @NonNull final Integer prevVersion,
                           @NonNull final Class<T> className) {
        try {
            log.info("Update DB record request for record {}", record);
            final Expression expression = Expression.builder()
                    .expression("Version = :version")
//                    .expressionNames(Map.of("version", "Version"))
                    .expressionValues(Map.of(":version", AttributeValue.builder().n(String.valueOf(prevVersion))
                            .build()))
                    .build();
            final UpdateItemEnhancedRequest<T> updateItemEnhancedRequest = UpdateItemEnhancedRequest.builder(className)
                    .item(record)
                    .ignoreNulls(true)
                    .conditionExpression(expression)
                    .build();
            dynamoDbTable.updateItem(updateItemEnhancedRequest);
            log.info("Update DB record request processed successfully");
        } catch (final ConditionalCheckFailedException e) {
            throw new RetryableException("Record is already updated with same version try after some interval", e);
        } catch (final TransactionCanceledException e) {
            throw new InvalidRequestException("failed to write record due to invalid request", e);
        } catch (final DynamoDbException e) {
            throw new RetryableDependencyException("Failed to save records", e);
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


    /**
     * method to get records by index when secondary index has only partition key
     *
     * @param indexName                name of index
     * @param indexPartitionKey        partition key of secondary index
     * @param pageSize                 numbers of records per page
     * @param lastEvaluatedKey         lastEvaluatedKey for pagination
     * @return
     */
    public PaginatedResult<T> getRecordByIndex(@NonNull final String indexName,
                                               @NonNull final String indexPartitionKey,
                                               @NonNull final Integer pageSize,
                                               final Map<String, AttributeValue> lastEvaluatedKey) {
        final QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(indexPartitionKey)
                .build());

        if (pageSize.compareTo(0) < 1) {
            throw new InvalidRequestException("Page Size can not be zero or negative");
        }

        final QueryEnhancedRequest.Builder queryReqBuilder = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .limit(pageSize)
                .exclusiveStartKey(lastEvaluatedKey)
                .scanIndexForward(false);//TODO we can conditionally set to decide asc or desc order

        return getRecordsByIndex(indexName, queryReqBuilder.build());
    }

    /**
     * @param indexName            name of secondary index
     * @param queryEnhancedRequest request object
     * @return list of records if found else empty list
     */
    public PaginatedResult<T> getRecordsByIndex(@NonNull final String indexName,
                                                @NonNull final QueryEnhancedRequest queryEnhancedRequest) {
        try {
            log.info("Fetching record by QueryEnhancedRequest {} of index {}", queryEnhancedRequest, indexName);

            final SdkIterable<Page<T>> pages = dynamoDbTable.index(indexName)
                    .query(queryEnhancedRequest);
            final List<T> queriedRecords = new ArrayList<>();

            pages.stream().limit(1).forEach(page -> {
                queriedRecords.addAll(page.items());
            });

            Map<String, AttributeValue> lastEvaluatedKey = null;
            for (Page<T> page : pages) {
                queriedRecords.addAll(page.items());
                if (page.lastEvaluatedKey() != null) {
                    lastEvaluatedKey = page.lastEvaluatedKey();
                    break;
                }
            }

            log.info("Queried result {}", queriedRecords);
            return new PaginatedResult<>(queriedRecords, lastEvaluatedKey);
        } catch (final IndexNotFoundException e) {
            throw new NonRetryableException("Index not found in table" +
                    " to query using index " + indexName + " queryEnhancedRequest " + queryEnhancedRequest);
        } catch (final DynamoDbException e) {
            throw new RetryableDependencyException("Failed to query table db record " +
                    "for queryEnhancedRequest : " + queryEnhancedRequest, e);
        }
    }

}