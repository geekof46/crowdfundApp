package org.crowdfund.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Data
@ToString
public class DonationSaveRequest {
    private String projectId;
    private BigDecimal donationAmount;
    private String comment;
}
