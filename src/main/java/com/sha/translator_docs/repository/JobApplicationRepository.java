package com.sha.translator_docs.repository;

import com.sha.translator_docs.model.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    // List all User related application
    @Query("SELECT ja FROM JobApplication ja WHERE ja.user.id = :userId")
    Page<JobApplication> findAllByUserId(Long userId, Pageable pageable);

    // List all Company related application
    @Query("""
        SELECT ja FROM JobApplication ja 
        WHERE ja.jobVacancy.id = :jobId 
        AND ja.jobVacancy.company.id = :companyId
        ORDER BY 
            CASE WHEN ja.score IS NULL THEN 1 ELSE 0 END,
            ja.score DESC
    """)
    Page<JobApplication> findByJobVacancyIdAndCompanyId(
            @Param("jobId") Long jobVacancyId,
            @Param("companyId") Long companyId,
            Pageable pageable
    );

    // Evaluate if User has already applied to the application
    @Query("SELECT ja FROM JobApplication ja WHERE ja.user.id = :userId AND ja.jobVacancy.id = :jobId")
    Optional<JobApplication> findByUserIdAndJobVacancyId(@Param("userId") Long userId, @Param("jobId") Long jobId);
    }
