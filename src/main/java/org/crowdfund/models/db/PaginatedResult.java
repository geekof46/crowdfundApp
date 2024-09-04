package org.crowdfund.models.db;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

/**
 * The type Paginated result.
 *
 * @param <T> the type parameter
 */
@ToString
@Builder
@Getter
@NoArgsConstructor
public class PaginatedResult<T> {
    private List<T> records;
    private Map<String, AttributeValue> lastEvaluatedKey;

    /**
     * Instantiates a new Paginated result.
     *
     * @param records          the records
     * @param lastEvaluatedKey the last evaluated key
     */
    public PaginatedResult(@NonNull final List<T> records,
                           final Map<String, AttributeValue> lastEvaluatedKey) {
        this.records = records;
        this.lastEvaluatedKey = lastEvaluatedKey;
    }
}
