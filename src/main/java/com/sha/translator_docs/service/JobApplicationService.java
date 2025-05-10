package com.sha.translator_docs.service;

import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationCsvDTO;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationResponseDTO;
import com.sha.translator_docs.model.StatusApplication;
import com.sha.translator_docs.model.User;
import com.sha.translator_docs.model.JobApplication;
import java.util.List;

public interface JobApplicationService {
    // Save the user to an application
    JobApplication applyToJob(Long jobVacancyId, User user, JobApplicationCsvDTO csvData);


    // List all user application
    List<JobApplicationResponseDTO> getAllApplicationsByUser(Long userId);

    // Company sets the user score
    void updateStatusOrScore(Long applicationId, StatusApplication newStatus, Integer newScore, User currentUser);
}