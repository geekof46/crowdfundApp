package org.crowdfund.controller;


import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.models.PaginatedResultDTO;
import org.crowdfund.models.ProjectCategory;
import org.crowdfund.models.ProjectDTO;
import org.crowdfund.models.ProjectSaveDTO;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.pojo.ProjectSaveRequest;
import org.crowdfund.service.ProjectService;
import org.crowdfund.utils.ProjectCategoryUtil;
import org.crowdfund.utils.UniqueIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

/**
 * Controller class for Project
 */
@Log4j2
@RestController
@EnableWebMvc
@RequestMapping("/api/v1")
public class ProjectController {

    private static final String PROJECT_ID_PREFIX = "PROJ-";
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * method to get projects by status
     *
     * @param status   status of project
     * @param pageSize no of records per page
     * @param next     sort key of next record to fetch
     * @return list of projects
     */
    @GetMapping("/users/{innovatorId}/projects")
    public ResponseEntity<PaginatedResultDTO<ProjectDTO>> getProjectsByStatus(
            @PathVariable("innovatorId") String innovatorId,
            @RequestParam("status") @NonNull final String status,
            @RequestParam("pageSize") @NonNull final Integer pageSize,
            @RequestParam("next") final String next
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.getProjectsByInnovatorIdAndStatus(innovatorId,
                        ProjectStatus.valueOf(status),
                        pageSize, next));
    }

    /**
     * method to get projects by status those are for donation
     * by user
     *
     * @param status   status of project
     * @param pageSize no of records per page
     * @param next     sort key of next record to fetch
     * @return list of projects
     */
    //TODO we can improve this api later to support different filtering for projects
    @GetMapping("/projects")
    public ResponseEntity<PaginatedResultDTO<ProjectDTO>> getProjectsForDonation(
            @RequestParam("status") @NonNull final String status,
            @RequestParam("pageSize") @NonNull final Integer pageSize,
            @RequestParam("next") final String next,
            /* TODO read it from jwt token */
            @RequestParam("userId") final String userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(projectService.getProjectForDonation(userId,
                        ProjectStatus.valueOf(status),
                        pageSize, next));
    }

    /**
     * method get project by id
     *
     * @param projectId unique identifier for project
     * @return project with matched projectId
     */
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ProjectDTO> getProjectById(
            @NonNull final @PathVariable("projectId") String projectId) {
        log.info("request for get project {}", projectId);
        final ProjectDTO projectDTO = projectService.getProjectById(projectId);
        return projectDTO != null ? ResponseEntity.ok(projectDTO)
                : ResponseEntity.notFound().build();
    }

    /**
     * method to add new project
     *
     * @param request project create request
     * @return response with success or failure
     */
    @PostMapping("/users/{innovatorId}/projects")
    public ResponseEntity<Object> addProject(
            @PathVariable("innovatorId") String innovatorId,
            @RequestBody final ProjectSaveRequest request) {
        projectService.save(buildProjectSaveDTO(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Project created successfully");
    }

    /**
     * method to build ProjectSaveDTO object from request
     * We will be assigning project donation date as next day and project donation end date
     * as next 60 days after start
     *
     * @param request object to save Project
     * @return object of type ProjectSaveDTO
     */
    private @NonNull ProjectSaveDTO buildProjectSaveDTO(@NonNull final ProjectSaveRequest request) {

        final Instant currentSystemTime = Instant.now();
        final ProjectSaveDTO.ProjectSaveDTOBuilder projectSaveDTOBuilder = ProjectSaveDTO.builder()
                .projectId(UniqueIdGenerator.generateUUID(PROJECT_ID_PREFIX))
                .innovatorId(request.getInnovatorId())
                .description(request.getDescription())
                .status(ProjectStatus.REQUESTED)
                .receivedDonationAmount(BigDecimal.ZERO)
                .donationStartDate(currentSystemTime.plus(Duration.ofDays(1)))
                .donationEndDate(currentSystemTime.plus(Duration.ofDays(61)));

        if (BigDecimal.ZERO.compareTo(request.getRequestedAmount()) >= 0) {
            throw new InvalidRequestException("Project expected donation amount should be non zero positive value");
        }

        final ProjectCategory category = ProjectCategoryUtil.getProjectCategory(request.getCategory()
                .toUpperCase().trim());
        projectSaveDTOBuilder.name(request.getName());
        projectSaveDTOBuilder.category(category);
        projectSaveDTOBuilder.subCategory(ProjectCategoryUtil.getProjectSubCategory(category,
                request.getSubCategory().toUpperCase()).toString());

        return projectSaveDTOBuilder
                .requestedAmount(request.getRequestedAmount())
                .build();
    }
}