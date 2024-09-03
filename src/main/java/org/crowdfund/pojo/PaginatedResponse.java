package org.crowdfund.pojo;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@ToString
@Data
public class PaginatedResponse<T> {
    private T data;
    private Pagination pagination;
}
