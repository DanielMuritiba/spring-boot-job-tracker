package com.sha.translator_docs.service;

import com.sha.translator_docs.DTO.JobVacancyDTO.JobVacancyMapper;
import com.sha.translator_docs.DTO.JobVacancyDTO.JobVacancyRequestDTO;
import com.sha.translator_docs.DTO.JobVacancyDTO.JobVacancyResponseDTO;
import com.sha.translator_docs.model.JobVacancy;
import com.sha.translator_docs.model.User;
import com.sha.translator_docs.repository.JobVacancyRepository;
import com.sha.translator_docs.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public JobVacancyResponseDTO saveJobVacancy(JobVacancyRequestDTO dto, Long companyId) {
        User company = userRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + companyId));

        JobVacancy entity = JobVacancyMapper.toEntity(dto);
        entity.setCompany(company);
        entity.setCreateTime(LocalDateTime.now());

        JobVacancy saved = jobVacancyRepository.save(entity);
        return JobVacancyMapper.toDTO(saved);
    }

    @Override
    public void deleteJobVacancy(Long jobVacancyId, Long companyId) {
        JobVacancy vacancy = jobVacancyRepository.findById(jobVacancyId)
                .orElseThrow(() -> new RuntimeException("Vacancy ID " + jobVacancyId + " not found"));

        if (!vacancy.getCompany().getId().equals(companyId)) {
            throw new RuntimeException("You are not authorized to delete this vacancy.");
        }

        jobVacancyRepository.delete(vacancy);
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
