package org.crowdfund.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.crowdfund.dao.DonationDao;
import org.crowdfund.models.DonationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DonationService {

    private final DonationDao donationDao;

    public DonationService(@NonNull final DonationDao donationDao) {
        this.donationDao = donationDao;
    }

    /**
     * @param donationDTO
     * @return
     */
    public void saveDonation(@NonNull final DonationDTO donationDTO) {
        donationDao.save(donationDTO);
    }

    /**
     * @param projectId
     * @return
     */
    public List<DonationDTO> getDonationsByProjectId(@NonNull final String projectId) {
        return donationDao.getDonationByProjectId(projectId);
    }

    /**
     * @param donorId
     * @return
     */
    public List<DonationDTO> getDonationsByDonorId(@NonNull final String donorId) {
        return donationDao.getDonationByDonarId(donorId);
    }
}

