package org.crowdfund.pojo;

import lombok.Builder;
import lombok.Data;
import org.crowdfund.models.ProjectStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Data
public class ProjectDTO {
    private String projectId;
    private String thumbnailLink;
    private String description;
    private String innovatorId;
    private BigDecimal expectAmount;
    /**
     * total amount collected after donation
     */
    private BigDecimal receivedAmount;
    private Instant donationDate;
    private ProjectStatus status;
    private Integer version;
    private Instant createDate;
    private Instant updateDate;
}
