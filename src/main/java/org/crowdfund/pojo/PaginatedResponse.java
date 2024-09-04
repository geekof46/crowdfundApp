package org.crowdfund.pojo;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * The type Paginated response.
 *
 * @param <T> the type parameter
 */
@Builder
@ToString
@Data
public class PaginatedResponse<T> {
    private T data;
    private Pagination pagination;
}
