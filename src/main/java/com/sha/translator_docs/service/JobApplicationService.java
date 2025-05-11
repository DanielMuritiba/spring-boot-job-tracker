package com.sha.translator_docs.service;

import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationCsvDTO;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationResponseDTO;
import com.sha.translator_docs.model.StatusApplication;
import com.sha.translator_docs.model.User;
import com.sha.translator_docs.model.JobApplication;
import com.sha.translator_docs.security.UserPrincipal;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobApplicationService {
    // Save the user to an application
    JobApplicationResponseDTO applyToJob(Long jobVacancyId, User user, JobApplicationCsvDTO csvData);

    // List all user related application
    Page<JobApplicationResponseDTO> getAllApplicationsByUser(Long userId, int page, int size);

    // Company sets the user score
    void updateStatusOrScore(Long applicationId, StatusApplication newStatus, Integer newScore, UserPrincipal principal);

    // List all company related application
    Page<JobApplicationResponseDTO> getApplicationsByJobVacancy(Long jobVacancyId, Long companyId, int page, int size);
}