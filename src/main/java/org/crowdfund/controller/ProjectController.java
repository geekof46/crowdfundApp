package org.crowdfund.controller;


import org.crowdfund.models.DonationDTO;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.models.db.Donation;
import org.crowdfund.pojo.ProjectDTO;
import org.crowdfund.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@EnableWebMvc
@RequestMapping("/api/v1")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable String projectId) {
        final ProjectDTO projectDTO = projectService.getProjectById(projectId);
        return  projectDTO == null ? ResponseEntity.ok().body(projectDTO)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/project")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> products = projectService.getProjects(ProjectStatus.REQUESTED);
        return ResponseEntity.ok(products);
    }

    /**
     * method for post mappoing
     * @param projectDTO
     * @return
     */
    // Create a new product
    @PostMapping("/project")
    public ResponseEntity<ProjectDTO> saveProject(@RequestBody ProjectDTO projectDTO) {
        //TODO convert input
        projectService.save(projectDTO);
        return ResponseEntity.ok(projectDTO);
    }

    /**
     * method for post mappoing
     * @param donationDTO
     * @return
     */
    // Create a new product
    @PostMapping("/project/donation")
    public ResponseEntity<DonationDTO> saveProject(@RequestBody DonationDTO donationDTO) {
        //TODO convert input
        projectService.addDonationToProject(donationDTO);
        return ResponseEntity.ok(donationDTO);
    }

}
