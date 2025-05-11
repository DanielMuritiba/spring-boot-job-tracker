package com.sha.translator_docs.service;

import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationCsvDTO;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationMapper;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationResponseDTO;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationUpdateRequestDTO;
import com.sha.translator_docs.model.*;
import com.sha.translator_docs.repository.JobApplicationRepository;
import com.sha.translator_docs.repository.JobVacancyRepository;
import com.sha.translator_docs.security.UserPrincipal;
import com.sha.translator_docs.service.JobApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;
    private final JobVacancyRepository jobVacancyRepository;

    @Override
    @Transactional
    public JobApplicationResponseDTO applyToJob(Long jobVacancyId, User user, JobApplicationCsvDTO csvData) {
        jobApplicationRepository.findByUserIdAndJobVacancyId(user.getId(), jobVacancyId)
                .ifPresent(existing -> {
                    throw new IllegalStateException("User has already applied.");
                });

        JobVacancy jobVacancy = jobVacancyRepository.findById(jobVacancyId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found."));

        JobApplication application = JobApplicationMapper.fromCsvDTO(csvData, user, jobVacancy);

        JobApplication saved = jobApplicationRepository.save(application);

        return JobApplicationMapper.toResponseDTO(saved);
    }
    @Override
    public Page<JobApplicationResponseDTO> getAllApplicationsByUser(Long userId, int page, int size) {
        Page<JobApplication> applications = jobApplicationRepository.findAllByUserId(userId, PageRequest.of(page, size));
        return applications.map(JobApplicationMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public void updateStatusOrScore(Long applicationId, StatusApplication newStatus, Integer newScore, UserPrincipal principal) {
        boolean isCompany = principal.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_COMPANY"));

        if (!isCompany) {
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
        System.out.println("Verificando transição: " + current + " -> " + next);
        return switch (current) {
            case APPLIED -> next == StatusApplication.INTERVIEW || next == StatusApplication.DISQUALIFIED;
            case INTERVIEW -> next == StatusApplication.APPROVED || next == StatusApplication.DISQUALIFIED;
            default -> false;
        };
    }

    @Override
    public Page<JobApplicationResponseDTO> getAllApplicationsByCompany(Long companyId, int page, int size) {
        Page<JobApplication> applications = jobApplicationRepository.findByCompanyId(companyId, PageRequest.of(page, size));
        return applications.map(JobApplicationMapper::toResponseDTO);
    }
}
