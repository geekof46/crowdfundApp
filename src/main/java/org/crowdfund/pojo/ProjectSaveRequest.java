package org.crowdfund.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crowdfund.models.ProjectCategory;

import java.math.BigDecimal;

/**
 * Class to read project save DTO
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSaveRequest {
    private String name;
    private String thumbnailLink;
    private String description;
    private String innovatorId;
    private String category;
    private String subCategory;
    private BigDecimal requestedAmount;
}
