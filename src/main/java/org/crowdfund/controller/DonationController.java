package org.crowdfund.controller;

import lombok.extern.slf4j.Slf4j;
import org.crowdfund.models.DonationDTO;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.pojo.ProjectDTO;
import org.crowdfund.service.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@EnableWebMvc
@Slf4j
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping("/donations/{projectId}")
    public ResponseEntity<List<DonationDTO>> getDonationByProjectId(@PathVariable String projectId) {
        final List<DonationDTO> donations = donationService.getDonationsByProjectId(projectId);
        return  !donations.isEmpty() ? ResponseEntity.ok().body(donations)
                : ResponseEntity.notFound().build();
    }
}
