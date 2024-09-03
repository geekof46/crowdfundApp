package org.crowdfund.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    @NonNull
    private String projectId;
    @NonNull
    private String name;
    private String thumbnailLink;
    @NonNull
    private String description;
    @NonNull
    private String innovatorId;
    @NonNull
    private ProjectStatus status;
    @NonNull
    private ProjectCategory category;
    @NonNull
    private String subCategory;
    @NonNull
    private BigDecimal requestedAmount;
    @NonNull
    private BigDecimal receivedDonationAmount;
    @NonNull
    private Instant donationStartDate;
    @NonNull
    private Instant donationEndDate;
}
