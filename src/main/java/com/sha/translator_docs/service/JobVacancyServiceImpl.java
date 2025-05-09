package com.sha.translator_docs.service;

import com.sha.translator_docs.DTO.DeviceDTO.JobVacancyMapper;
import com.sha.translator_docs.DTO.DeviceDTO.JobVacancyRequestDTO;
import com.sha.translator_docs.DTO.DeviceDTO.JobVacancyResponseDTO;
import com.sha.translator_docs.model.JobVacancy;
import com.sha.translator_docs.model.User;
import com.sha.translator_docs.repository.JobVacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobVacancyServiceImpl implements JobVacancyService {

    private final JobVacancyRepository jobVacancyRepository;

    @Autowired
    public JobVacancyServiceImpl(JobVacancyRepository jobVacancyRepository) {
        this.jobVacancyRepository = jobVacancyRepository;
    }

    @Override
    public JobVacancyResponseDTO saveJobVacancy(JobVacancyRequestDTO dto, User company) {
        JobVacancy entity = JobVacancyMapper.toEntity(dto);
        entity.setCompany(company);
        entity.setCreateTime(LocalDateTime.now());

        JobVacancy saved = jobVacancyRepository.save(entity);
        return JobVacancyMapper.toDTO(saved);
    }

    @Override
    public void deleteJobVacancy(Long id) {
        jobVacancyRepository.deleteById(id);
    }

    @Override
    public List<JobVacancyResponseDTO> findAllJobVacancy() {
        return jobVacancyRepository.findAll()
                .stream()
                .map(JobVacancyMapper::toDTO)
                .toList();
    }

    @Override
    public Page<JobVacancyResponseDTO> findByCompanyPaginated(Long companyId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createTime").descending());
        Page<JobVacancy> resultPage = jobVacancyRepository.findAllByCompanyId(companyId, pageable);
        return resultPage.map(JobVacancyMapper::toDTO);
    }
}
