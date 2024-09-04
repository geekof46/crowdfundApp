package org.crowdfund.controller;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.models.DonationDTO;
import org.crowdfund.models.DonationSaveDTO;
import org.crowdfund.models.PaginatedResultDTO;
import org.crowdfund.models.db.PaginatedResult;
import org.crowdfund.pojo.DonationSaveRequest;
import org.crowdfund.pojo.Response;
import org.crowdfund.service.DonationService;
import org.crowdfund.utils.UniqueIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;

/**
 * Controller class for Donation
 */
@Log4j2
@RestController
@EnableWebMvc
@RequestMapping("/api/v1")
public class DonationController {

    private static final String DONATION_ID_PREFIX = "DONAT-";
    private final DonationService donationService;

    /**
     * Instantiates a new Donation controller.
     *
     * @param donationService the donation service
     */
    @Autowired
    public DonationController(@NonNull final DonationService donationService) {
        this.donationService = donationService;
    }

    /**
     * method to get donation done on project
     *
     * @param projectId the project id
     * @param pageSize  number of donation records per page
     * @param next      next record to start with
     * @return list of donations
     */
    @GetMapping("/projects/{projectId}/donations")
    public ResponseEntity<PaginatedResultDTO<DonationDTO>> getDonationByProjectId(
            @PathVariable("projectId") final String projectId,
            @RequestParam("pageSize") final Integer pageSize,
            @RequestParam("next") final String next) {
        //TODO innovator should not donate on project created by him
        return ResponseEntity.status(HttpStatus.OK)
                .body(donationService.getDonationsByProjectId(projectId, pageSize, next));
    }


    /**
     * method to add new donation
     *
     * @param projectId the project id
     * @param request   project create request
     * @return response with success or failure
     */
    @PostMapping("/projects/{projectId}/donations")
    public ResponseEntity<Object> addDonation(
            @PathVariable("projectId") final String projectId,
            @RequestBody final DonationSaveRequest request) {
        //TODO add donationId as userId who is calling API
        donationService.saveDonation(buildDonationSaveDTO(request, "donorId", projectId));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Donation done successfully");
    }

    /**
     * method to get donation done by donorId
     *
     * @param donorId  userId
     * @param pageSize number of donation records per page
     * @param next     next record to start with
     * @return list of donations
     */
    @GetMapping("/users/{donorId}/donations")
    public ResponseEntity<PaginatedResultDTO<DonationDTO>> getDonationByDonorId(
            @PathVariable("donorId") final String donorId,
            @RequestParam("pageSize") final Integer pageSize,
            @RequestParam("next") final String next) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(donationService.getDonationsByDonorId(donorId, pageSize, next));
    }

    private @NonNull DonationSaveDTO buildDonationSaveDTO(@NonNull final DonationSaveRequest request,
                                                          @NonNull final String donorId,
                                                          @NonNull final String projectId) {
        final DonationSaveDTO.DonationSaveDTOBuilder builder = DonationSaveDTO.builder()
                .donationId(UniqueIdGenerator.generateUUID(DONATION_ID_PREFIX))
                .donorId(donorId)
                .projectId(projectId);


        if (StringUtils.hasText(request.getComment())) {
            builder.comment(request.getComment());
        }

        if (BigDecimal.ZERO.compareTo(request.getDonationAmount()) >= 0) {
            throw new InvalidRequestException("Donation amount should be non zero positive value");
        }

        return builder
                .donationAmount(request.getDonationAmount())
                .build();
    }

}


