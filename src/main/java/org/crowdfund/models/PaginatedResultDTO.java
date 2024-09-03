package org.crowdfund.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;

@ToString
@Builder
@Getter
@NoArgsConstructor
public class PaginatedResultDTO<T> {
    @NonNull
    private List<T> records;
    @NonNull
    private String next;

    public PaginatedResultDTO(@NonNull final List<T> records,
                              @NonNull final String next) {
        this.records = records;
        this.next = next;
    }
}
