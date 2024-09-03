package org.crowdfund.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * model to save new donation
 */
@ToString
@Builder
@Getter
public class DonationSaveDTO {
    @NonNull
    private String donationId;
    @NonNull
    private String donorId;
    @NonNull
    private String projectId;
    @NonNull
    private BigDecimal donationAmount;
    private String comment;
}
