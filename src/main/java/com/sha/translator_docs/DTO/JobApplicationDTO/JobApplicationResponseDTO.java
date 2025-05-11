package com.sha.translator_docs.DTO.JobApplicationDTO;

import lombok.Data;

@Data
public class JobApplicationResponseDTO {
    private Long applicationId;
    private String userUsername;
    private String userDescription;
    private String userSkills;
    private String jobName;
    private String status;
    private String score;
}
