package com.sha.translator_docs.repository;

import com.sha.translator_docs.model.JobVacancy;
import com.sha.translator_docs.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobVacancyRepository extends JpaRepository<JobVacancy, Long> {

    Page<JobVacancy> findAllByCompanyId(Long companyId, Pageable pageable);
}
