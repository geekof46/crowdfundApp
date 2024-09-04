package org.crowdfund.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * The type Project dto.
 */
@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private String projectId;
    private String name;
    private String thumbnailLink;
    private String description;
    private ProjectCategory category;
    private String subCategory;
    private String innovatorId;
    private BigDecimal requestedAmount;
    private BigDecimal receivedDonationAmount;
    private ProjectStatus status;
    private Integer version;
    private Instant donationStartDate;
    private Instant donationEndDate;
    private Instant createDate;
    private Instant updateDate;
}
