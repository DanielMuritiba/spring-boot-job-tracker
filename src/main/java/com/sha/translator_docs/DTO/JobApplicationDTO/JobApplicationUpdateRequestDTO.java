package com.sha.translator_docs.DTO.JobApplicationDTO;

import com.sha.translator_docs.model.StatusApplication;
import lombok.Data;

@Data
public class JobApplicationUpdateRequestDTO {
    private Long applicationId;
    private StatusApplication newStatus;
    private Integer newScore;
}
