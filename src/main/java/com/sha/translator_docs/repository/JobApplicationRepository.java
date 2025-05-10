package com.sha.translator_docs.repository;

import com.sha.translator_docs.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    // List all User application
    @Query("SELECT ja FROM JobApplication ja WHERE ja.user.id = :userId")
    List<JobApplication> findAllByUserId(@Param("userId") Long userId);

    // Evaluate if User has already applied to the application
    @Query("SELECT ja FROM JobApplication ja WHERE ja.user.id = :userId AND ja.jobVacancy.id = :jobId")
    Optional<JobApplication> findByUserIdAndJobVacancyId(@Param("userId") Long userId, @Param("jobId") Long jobId);
    }
