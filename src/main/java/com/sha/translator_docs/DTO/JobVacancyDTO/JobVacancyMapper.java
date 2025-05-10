package com.sha.translator_docs.DTO.JobVacancyDTO;

import com.sha.translator_docs.model.JobVacancy;

public class JobVacancyMapper {

    public static JobVacancy toEntity(JobVacancyRequestDTO dto) {
        JobVacancy vacancy = new JobVacancy();
        vacancy.setId(dto.getId());
        vacancy.setJobName(dto.getJobName());
        vacancy.setRequiredSkills(dto.getRequiredSkills());
        vacancy.setDescription(dto.getDescription());
        return vacancy;
    }

    public static JobVacancyResponseDTO toDTO(JobVacancy entity) {
        JobVacancyResponseDTO dto = new JobVacancyResponseDTO();
        dto.setId(entity.getId());
        dto.setJobName(entity.getJobName());
        dto.setRequiredSkills(entity.getRequiredSkills());
        dto.setDescription(entity.getDescription());
        dto.setCreateTime(entity.getCreateTime());
        dto.setCompanyUsername(entity.getCompany().getUsername()); // ou getEmail()
        return dto;
    }
}

