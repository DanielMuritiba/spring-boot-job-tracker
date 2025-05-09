package com.sha.translator_docs.DTO.JobVacancyDTO;

import lombok.Data;

@Data
public class JobVacancyRequestDTO {
    private String jobName;
    private String requiredSkills;
    private String description;
}
