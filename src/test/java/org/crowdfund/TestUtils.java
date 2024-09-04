package org.crowdfund;

import org.crowdfund.models.PaginatedResultDTO;
import org.crowdfund.models.ProjectDTO;
import org.crowdfund.models.ProjectSaveDTO;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.models.db.PaginatedResult;
import org.crowdfund.models.db.Project;

import java.util.List;

public class TestUtils {

    private TestUtils(){}

    public static Project getProject() {
        return Project.builder()
                .projectId("test-project-id")
                .innovatorId("test-innovator-id")
                .status(ProjectStatus.REQUESTED)
                .build();
    }

    public static ProjectSaveDTO getProjectSaveDTO() {
        return ProjectSaveDTO.builder()
                .build();
    }

    public static ProjectDTO getProjectDTO() {
        return ProjectDTO.builder()
                .projectId("test-project-id")
                .innovatorId("test-innovator-id")
                .status(ProjectStatus.REQUESTED)
                .build();
    }

    public static PaginatedResult<Project> getPaginatedResult() {
        return PaginatedResult.<Project>builder()
                .records(List.of(getProject()))
                .build();
    }

    public static PaginatedResultDTO<ProjectDTO> getPaginatedDTOResult() {
        return PaginatedResultDTO.<ProjectDTO>builder()
                .records(List.of(getProjectDTO()))
                .next("")
                .build();
    }
}
