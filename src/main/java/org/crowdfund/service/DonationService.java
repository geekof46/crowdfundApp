package org.crowdfund.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.crowdfund.dao.DonationDao;
import org.crowdfund.models.DonationDTO;
import org.crowdfund.models.DonationSaveDTO;
import org.crowdfund.models.PaginatedResultDTO;
import org.crowdfund.models.db.PaginatedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to manage donation operations
 */
@Slf4j
@Service
public class DonationService {

    private final DonationDao donationDao;
    private final ProjectService projectService;

    /**
     * Instantiates a new Donation service.
     *
     * @param donationDao    the donation dao
     * @param projectService the project service
     */
    @Autowired
    public DonationService(@NonNull final DonationDao donationDao,
                           @NonNull final ProjectService projectService) {
        this.donationDao = donationDao;
        this.projectService = projectService;
    }

    /**
     * method to save donation
     *
     * @param donationSaveDTO save request object
     */
    public void saveDonation(@NonNull final DonationSaveDTO donationSaveDTO) {
        projectService.updateProjectForDonationAmount(donationSaveDTO.getProjectId(),
                donationSaveDTO.getDonorId(),
                donationSaveDTO.getDonationAmount());
        donationDao.save(donationSaveDTO);
    }


    /**
     * method to fetch donation done on project
     *
     * @param projectId   project unique identifier
     * @param projectSize number of records per page
     * @param next        next record to start with
     * @return list of donations done on project
     */
    public PaginatedResultDTO<DonationDTO> getDonationsByProjectId(@NonNull final String projectId,
                                                                @NonNull final Integer projectSize,
                                                                @NonNull final String next) {
        //validate for project exists
        projectService.getProjectById(projectId);
        return donationDao.getByProjectId(projectId, projectSize,
                next);
    }

    /**
     * method to fetch list of donations by donorId
     *
     * @param donorId  user who donates to Project
     * @param pageSize the page size
     * @param next     the next
     * @return list of donations
     */
    public PaginatedResultDTO<DonationDTO> getDonationsByDonorId(@NonNull final String donorId,
                                                                 @NonNull final Integer pageSize,
                                                                 @NonNull final String next) {
        return donationDao.getByDonorId(donorId, pageSize, next);
    }
}

