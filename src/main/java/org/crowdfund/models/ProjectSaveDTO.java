package org.crowdfund.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Class to read project save DTO
 */
@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSaveDTO {

    private String projectId;
    private String name;
    private String thumbnailLink;
    private String description;
    private String innovatorId;
    private ProjectStatus status;

    private ProjectCategory category;

    private String subCategory;

    private BigDecimal requestedAmount;

    private BigDecimal receivedDonationAmount;
    private Instant donationStartDate;
    private Instant donationEndDate;
}
