package com.sha.translator_docs.service;

import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationCsvDTO;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationResponseDTO;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationUpdateRequestDTO;
import com.sha.translator_docs.model.*;
import com.sha.translator_docs.repository.JobApplicationRepository;
import com.sha.translator_docs.repository.JobVacancyRepository;
import com.sha.translator_docs.service.JobApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobVacancyRepository jobVacancyRepository;

    @Override
    @Transactional
    public JobApplication applyToJob(Long jobVacancyId, User user, JobApplicationCsvDTO csvData) {
        jobApplicationRepository.findByUserIdAndJobVacancyId(user.getId(), jobVacancyId)
                .ifPresent(existing -> {
                    throw new IllegalStateException("User has already applied.");
                });

        JobVacancy jobVacancy = jobVacancyRepository.findById(jobVacancyId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found."));

        JobApplication application = new JobApplication();
        application.setUser(user);
        application.setJobVacancy(jobVacancy);
        application.setStatusApplication(StatusApplication.APPLIED);
        application.setEmail(csvData.getEmail());
        application.setSkills(csvData.getSkills());
        application.setDescription(csvData.getDescription());
        application.setUserUsername(csvData.getUsername());

        return jobApplicationRepository.save(application);
    }

    @Override
    public List<JobApplicationResponseDTO> getAllApplicationsByUser(Long userId) {
        List<JobApplication> applications = jobApplicationRepository.findAllByUserId(userId);

        return applications.stream().map(app -> {
            JobApplicationResponseDTO dto = new JobApplicationResponseDTO();
            dto.setApplicationId(app.getId());
            dto.setJobName(app.getJobVacancy().getJobName());
            dto.setStatus(app.getStatusApplication().name());
            dto.setScore(app.getScore() != null ? String.valueOf(app.getScore()) : "Not evaluated");
            return dto;
        }).toList();
    }

    @Override
    @Transactional
    public void updateStatusOrScore(Long applicationId, StatusApplication newStatus, Integer newScore, User currentUser) {
        if (currentUser.getRole() != Role.COMPANY) {
            throw new SecurityException("Only company can do this action.");
        }

        JobApplication app = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found."));

        if (newScore != null) {
            app.setScore(newScore);
        }

        if (newStatus != null && isValidStatusTransition(app.getStatusApplication(), newStatus)) {
            app.setStatusApplication(newStatus);
        }

        jobApplicationRepository.save(app);
    }

    private boolean isValidStatusTransition(StatusApplication current, StatusApplication next) {
        return switch (current) {
            case APPLIED -> next == StatusApplication.INTERVIEW || next == StatusApplication.DISQUALIFIED;
            case INTERVIEW -> next == StatusApplication.APPROVED || next == StatusApplication.DISQUALIFIED;
            default -> false;
        };
    }
}
