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
    WHERE ja.jobVacancy.company.id = :companyId
    ORDER BY 
        CASE WHEN ja.score IS NULL THEN 1 ELSE 0 END,
        ja.score DESC,
        CASE ja.statusApplication
            WHEN 'APPROVED' THEN 1
            WHEN 'INTERVIEW' THEN 2
            WHEN 'APPLIED' THEN 3
            WHEN 'DISQUALIFIED' THEN 4
            ELSE 5
        END
    """)
    Page<JobApplication> findByCompanyId(@Param("companyId") Long companyId, Pageable pageable);


    // Evaluate if User has already applied to the application
    @Query("SELECT ja FROM JobApplication ja WHERE ja.user.id = :userId AND ja.jobVacancy.id = :jobId")
    Optional<JobApplication> findByUserIdAndJobVacancyId(@Param("userId") Long userId, @Param("jobId") Long jobId);
}
