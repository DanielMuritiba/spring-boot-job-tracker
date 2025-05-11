package com.sha.translator_docs.DTO.JobApplicationDTO;

import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationCsvDTO;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationResponseDTO;
import com.sha.translator_docs.model.JobApplication;
import com.sha.translator_docs.model.JobVacancy;
import com.sha.translator_docs.model.StatusApplication;
import com.sha.translator_docs.model.User;

public class JobApplicationMapper {

    public static JobApplicationResponseDTO toResponseDTO(JobApplication app) {
        JobApplicationResponseDTO dto = new JobApplicationResponseDTO();
        dto.setApplicationId(app.getId());
        dto.setUserUsername(app.getUser().getUsername());
        dto.setUserDescription(app.getDescription());
        dto.setUserSkills(app.getSkills());
        dto.setJobName(app.getJobVacancy().getJobName());
        dto.setStatus(app.getStatusApplication().name());
        dto.setScore(app.getScore() != null ? String.valueOf(app.getScore()) : "Not evaluated");
        return dto;
    }

    public static JobApplication fromCsvDTO(JobApplicationCsvDTO dto, User user, JobVacancy jobVacancy) {
        JobApplication application = new JobApplication();
        application.setUser(user);
        application.setJobVacancy(jobVacancy);
        application.setStatusApplication(StatusApplication.APPLIED);
        application.setEmail(dto.getEmail());
        application.setSkills(dto.getSkills());
        application.setDescription(dto.getDescription());
        application.setUserUsername(dto.getUsername());
        return application;
    }
}
