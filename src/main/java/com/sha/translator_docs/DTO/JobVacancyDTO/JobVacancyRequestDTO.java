package com.sha.translator_docs.DTO.JobVacancyDTO;

import lombok.Data;

@Data
public class JobVacancyRequestDTO {
    private Long id;
    private String jobName;
    private String requiredSkills;
    private String description;
}
