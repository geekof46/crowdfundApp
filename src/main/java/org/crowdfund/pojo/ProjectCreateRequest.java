package org.crowdfund.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * The type Project create request.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateRequest {
    @NonNull
    private String projectId;
    //TODO add feature to support uploading image
    private String thumbnailLink;
    @NonNull
    private String description;
    @NonNull
    private String innovatorId;
    @NonNull
    private BigDecimal expectAmount;
}
