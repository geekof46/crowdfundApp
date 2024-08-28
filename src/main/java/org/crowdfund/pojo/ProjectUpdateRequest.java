package org.crowdfund.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUpdateRequest {
    //TODO add feature to support uploading image
    private String thumbnailLink;
    private String description;
    private String innovatorId;
    private BigDecimal expectAmount;
    private BigDecimal receivedAmount;
    private String status;
}
