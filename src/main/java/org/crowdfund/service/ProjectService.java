package org.crowdfund.service;

import lombok.NonNull;
import org.crowdfund.dao.DonationDao;
import org.crowdfund.dao.ProjectDao;
import org.crowdfund.models.DonationDTO;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.pojo.ProjectDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjectService {


    private final ProjectDao projectDao;
    private final DonationDao donationDao;

    public ProjectService(@NonNull final ProjectDao projectDao,
                          @NonNull final DonationDao donationDao) {
        this.projectDao = projectDao;
        this.donationDao = donationDao;
    }

    /**
     * @param projectDTO
     */
    public void save(@NonNull final ProjectDTO projectDTO) {
        projectDao.save(projectDTO);
    }

    /**
     * @param projectStatus
     * @return
     */
    public List<ProjectDTO> getProjects(@NonNull final ProjectStatus projectStatus) {
        return projectDao.getProjectByStatus(projectStatus);
    }

    /**
     * @param projectId
     * @return
     */
    public ProjectDTO getProjectById(@NonNull final String projectId) {
        return projectDao.getProjectById(projectId);
    }

    public void addDonationToProject(DonationDTO donationDTO) {

        /**
         * TODO changes input to request object
         */
        ProjectDTO projectDTO = projectDao.getProjectById("ProjectId");
        final BigDecimal amountTobeUpdated = projectDTO.getReceivedAmount().add(donationDTO.getDonationAmount());
        if(amountTobeUpdated.compareTo(projectDTO.getExpectAmount()) == 1){
            // throw error amount is greater
            return;

        }
        //TODO create new object for save
        projectDTO.setReceivedAmount(amountTobeUpdated);
        projectDTO.setVersion(projectDTO.getVersion()+1);
        projectDao.save(projectDTO);
        donationDao.save(donationDTO);
    }
}
