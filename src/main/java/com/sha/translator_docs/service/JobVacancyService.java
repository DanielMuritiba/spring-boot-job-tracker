package com.sha.translator_docs.service;


import com.sha.translator_docs.DTO.DeviceDTO.JobVacancyRequestDTO;
import com.sha.translator_docs.DTO.DeviceDTO.JobVacancyResponseDTO;
import com.sha.translator_docs.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobVacancyService {
    JobVacancyResponseDTO saveJobVacancy(JobVacancyRequestDTO dto, User company);

    void deleteJobVacancy(Long id);

    List<JobVacancyResponseDTO> findAllJobVacancy();

    Page<JobVacancyResponseDTO> findByCompanyPaginated(Long companyId, int page, int size);
}
