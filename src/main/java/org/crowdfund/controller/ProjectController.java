package org.crowdfund.controller;


import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.models.DonationDTO;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.models.db.Project;
import org.crowdfund.pojo.ProjectDTO;
import org.crowdfund.pojo.ProjectCreateRequest;
import org.crowdfund.pojo.ProjectUpdateRequest;
import org.crowdfund.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

/**
 * Controller class for Project
 */
@Log4j2
@RestController
@EnableWebMvc
@RequestMapping("/api/v1")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * method get project by id
     * @param projectId
     * @return project with matched projectId
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable String projectId) {
        log.info("request for get project {}", projectId);
        final ProjectDTO projectDTO = projectService.getProjectById(projectId);
        return  projectDTO != null ? ResponseEntity.ok(projectDTO)
                : ResponseEntity.notFound().build();
    }


    /**
     * method to get projects by status
     *
     * @param status status of project
     * @param limit no of records per page
     * @param next sort key of next record to fetch
     * @return list of projects
     */
    @GetMapping("/project")
    public ResponseEntity<List<ProjectDTO>> getProjectsByStatus(
            @RequestParam("status") @NonNull final String status,
            @RequestParam("limit") @NonNull final Integer limit,
            @RequestParam("next") final String next
    ) {
        return ResponseEntity.ok(projectService.getProjectsByStatus(ProjectStatus.valueOf(status),
                limit,next));
    }

    @PostMapping("/project")
    public ResponseEntity<Object> addProject(@RequestBody @NonNull final ProjectCreateRequest putRequest) {

        projectService.save(buildProjectDTO(putRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Project created successfully");
    }

    @PutMapping("/project/{projectId}")
    public ResponseEntity<Object> updateProject(@RequestBody @NonNull final ProjectUpdateRequest putRequest,
                                                @PathVariable @NonNull final String projectId) {

        projectService.updateProject(buildProjectDTO(projectId,putRequest));
        return ResponseEntity.status(HttpStatus.OK)
                .body("Project updated successfully");
    }

    @PutMapping("/project/{projectId}/donation")
    public ResponseEntity<Object> addDonation(@RequestBody DonationDTO donationDTO) {
        projectService.addDonationToProject(donationDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Donation added successfully");
    }

    private @NonNull ProjectDTO buildProjectDTO(@NonNull final String projectId,
                                                @NonNull final ProjectUpdateRequest putRequest) {
        return ProjectDTO.builder()
                .projectId(projectId)
                .status(ProjectStatus.valueOf(putRequest.getStatus()))
                .receivedAmount(putRequest.getReceivedAmount())
                .innovatorId(putRequest.getInnovatorId())
                .build();
    }

    /**
     * method to build put object from request
     *
     * @param putRequest
     * @return
     */
    private @NonNull ProjectDTO buildProjectDTO(@NonNull final ProjectCreateRequest putRequest) {
        //TODO add validation for fields
        return ProjectDTO.builder()
                .projectId(putRequest.getProjectId())
                .description(putRequest.getDescription())
                .status(ProjectStatus.CREATED)
                .expectAmount(putRequest.getExpectAmount())
                .innovatorId(putRequest.getInnovatorId())
                .build();
    }

}
