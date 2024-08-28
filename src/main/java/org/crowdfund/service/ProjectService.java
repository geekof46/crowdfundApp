package org.crowdfund.service;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.dao.ProjectDao;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.exceptions.ProjectNotFoundException;
import org.crowdfund.models.DonationDTO;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.pojo.ProjectDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
@Service
public class ProjectService {


    private final ProjectDao projectDao;

    public ProjectService(@NonNull final ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    /**
     * @param projectDTO
     */
    public void save(@NonNull final ProjectDTO projectDTO) {
        projectDao.save(projectDTO);
    }


    public List<ProjectDTO> getProjectsByStatus(@NonNull final ProjectStatus status,
                                                final Integer limit,
                                                final String next) {
        return projectDao.getProjectsByStatus(status,
                limit, next);
    }


    public List<ProjectDTO> getProjectsByInnovatorIdAndStatus(
            @NonNull final String innovatorId,
            @NonNull final ProjectStatus status,
            @NonNull final Integer limit,
            @NonNull final String next) {
        return projectDao.getProjectsByInnovatorIdAndStatus(innovatorId,
                status,
                limit, next);
    }

    /**
     * @param projectId
     * @return
     */
    public ProjectDTO getProjectById(@NonNull final String projectId) {
        log.info("Fetching project for projectId {}", projectId);
        return projectDao.getProjectById(projectId);
    }

    public void addDonationToProject(DonationDTO donationDTO) {

        /**
         * TODO changes input to request object
         */
        ProjectDTO projectDTO = projectDao.getProjectById("ProjectId");
        final BigDecimal amountTobeUpdated = projectDTO.getReceivedAmount().add(donationDTO.getDonationAmount());
        if (amountTobeUpdated.compareTo(projectDTO.getExpectAmount()) == 1) {
            // throw error amount is greater

        }
        //TODO create new object for save
//        projectDTO.setReceivedAmount(amountTobeUpdated);
//        projectDTO.setVersion(projectDTO.getVersion()+1);
//        projectDao.save(projectDTO);
//        donationDao.save(donationDTO);
    }

    public void updateProject(@NonNull final ProjectDTO requestDTO) {
        try {
            final ProjectDTO projectDTO = projectDao.getProjectById(requestDTO.getProjectId());

            //TODO validated all fields and update
            //TODO also handle for concurrent update we should fail this and will be retried
            final ProjectDTO.ProjectDTOBuilder updatedDTO = projectDTO.toBuilder();
            if (requestDTO.getStatus() != null) {
                updatedDTO.status(requestDTO.getStatus());
            }

            if (requestDTO.getReceivedAmount() != null) {
                updatedDTO.receivedAmount(requestDTO.getReceivedAmount());
            }
            projectDao.save(updatedDTO.build());

        } catch (final ProjectNotFoundException e) {
            throw new InvalidRequestException("Invalid request with projectId " +
                    requestDTO.getProjectId());
        }
    }


}
