package org.crowdfund.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;

/**
 * The type Paginated result dto.
 *
 * @param <T> the type parameter
 */
@ToString
@Builder
@Getter
@NoArgsConstructor
public class PaginatedResultDTO<T> {
    @NonNull
    private List<T> records;
    @NonNull
    private String next;

    /**
     * Instantiates a new Paginated result dto.
     *
     * @param records the records
     * @param next    the next
     */
    public PaginatedResultDTO(@NonNull final List<T> records,
                              @NonNull final String next) {
        this.records = records;
        this.next = next;
    }
}
