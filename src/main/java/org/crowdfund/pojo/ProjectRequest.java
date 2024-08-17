package org.crowdfund.pojo;

import lombok.Data;
import org.crowdfund.models.db.ProjectStatus;

import java.time.Instant;

@Data
public class ProjectDTO {
    private String id;
    private String innovatorId;
    private Instant creationDate;
    private ProjectStatus status;
}
