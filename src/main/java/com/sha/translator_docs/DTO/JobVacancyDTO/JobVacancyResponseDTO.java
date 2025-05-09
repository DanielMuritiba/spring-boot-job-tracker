package com.sha.translator_docs.DTO.JobVacancyDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobVacancyResponseDTO {
    private Long id;
    private String jobName;
    private String requiredSkills;
    private String description;
    private LocalDateTime createTime;
    private String companyUsername;
}