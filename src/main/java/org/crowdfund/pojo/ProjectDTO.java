package org.crowdfund.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.crowdfund.models.ProjectStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    @NonNull
    private String projectId;
    private String thumbnailLink;
    private String description;
    private String innovatorId;
    private BigDecimal expectAmount;
    /**
     * total amount collected after donation
     */
    private BigDecimal receivedAmount;
    private ProjectStatus status;
    private Integer version;
    private Instant createDate;
    private Instant updateDate;
}
