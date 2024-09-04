package org.crowdfund.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.ToString;

/**
 * The type Pagination.
 */
@Builder
@ToString
@JsonSerialize
public class Pagination {
    private Integer pageSize;
    private String next;
}
