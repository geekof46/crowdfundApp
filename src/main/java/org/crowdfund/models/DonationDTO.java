package org.crowdfund.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@ToString
@Builder
@Getter
public class DonationDTO {
    private String donationId;
    private String donorId;
    private String projectId;
    private BigDecimal donationAmount;
    private String comment;
    private Instant donationDate;
}
