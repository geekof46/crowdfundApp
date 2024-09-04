package org.crowdfund.service;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.dao.ProjectDao;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.models.PaginatedResultDTO;
import org.crowdfund.models.ProjectDTO;
import org.crowdfund.models.ProjectSaveDTO;
import org.crowdfund.models.ProjectStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * service class to manage create/update project operations
 */
@Log4j2
@Service
public class ProjectService {


    private final ProjectDao projectDao;

    public ProjectService(@NonNull final ProjectDao projectDao) {
        this.projectDao = projectDao;
    }


    /**
     * method to save project
     *
     * @param projectSaveDTO project object to be saved
     */
    public void save(@NonNull final ProjectSaveDTO projectSaveDTO) {
        projectDao.save(projectSaveDTO);
    }


    public PaginatedResultDTO<ProjectDTO> getProjectsByInnovatorIdAndStatus(@NonNull final String innovatorId,
                                                                            @NonNull final ProjectStatus status,
                                                                            final Integer limit,
                                                                            final String next) {
        return projectDao.getProjectsByInnovatorIdAndStatus(innovatorId,
                status, limit, next);
    }

    /**
     * @param projectId
     * @return
     */
    public ProjectDTO getProjectById(@NonNull final String projectId) {
        log.info("Fetching project for projectId {}", projectId);
        return projectDao.getProjectById(projectId);
    }

    public void updateProjectForDonationAmount(@NonNull final String projectId,
                                               @NonNull final String donorId,
                                               @NonNull BigDecimal donationAmount) {
        //get project for donation
        final ProjectDTO projectDTO = projectDao.getProjectById(projectId);
        if (projectDTO == null) {
            throw new InvalidRequestException("Invalid request : Project not found");
        }
        if(projectDTO.getInnovatorId().equalsIgnoreCase(donorId)){
            throw new InvalidRequestException("Invalid request : Project belongs to same user");
        }

        if (ProjectStatus.ARCHIVED.equals(projectDTO.getStatus())) {
            throw new InvalidRequestException("Invalid Request : Project is archived no any further " +
                    "donation can be accepted");
        }

        final ProjectDTO.ProjectDTOBuilder updatedObject = projectDTO.toBuilder();
        final BigDecimal updatedDonationAmount = projectDTO.getReceivedDonationAmount().add(donationAmount);
        if (updatedDonationAmount.compareTo(projectDTO.getRequestedAmount()) >= 0) {
            updatedObject.status(ProjectStatus.ARCHIVED);
        }
        projectDao.updateProject(updatedObject.receivedDonationAmount(updatedDonationAmount).build());
    }

    public PaginatedResultDTO<ProjectDTO> getProjectForDonation(@NonNull final String userId,
                                                                @NonNull final ProjectStatus projectStatus,
                                                                @NonNull Integer pageSize,
                                                                @NonNull final String next) {
        return projectDao.getProjectsByStatusForDonation(userId, projectStatus, pageSize, next);
    }
}
