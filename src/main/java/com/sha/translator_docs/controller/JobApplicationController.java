package com.sha.translator_docs.controller;

import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationCsvDTO;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationResponseDTO;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationUpdateRequestDTO;
import com.sha.translator_docs.model.JobApplication;
import com.sha.translator_docs.model.User;
import com.sha.translator_docs.security.UserPrincipal;
import com.sha.translator_docs.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/job-application")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping("/{jobVacancyId}")
    public ResponseEntity<JobApplicationResponseDTO> applyToJob(
            @PathVariable Long jobVacancyId,
            @RequestBody JobApplicationCsvDTO csvData,
            Authentication authentication) {

        Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
        User user = new User();
        user.setId(userId);

        JobApplicationResponseDTO application = jobApplicationService.applyToJob(jobVacancyId, user, csvData);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<Page<JobApplicationResponseDTO>> getAllUserApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Authentication authentication) {

        Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(jobApplicationService.getAllApplicationsByUser(userId, page, size));
    }

    @PutMapping("/company-jobs")
    public ResponseEntity<Void> updateStatusOrScore(
            @RequestBody JobApplicationUpdateRequestDTO updateDTO,
            Authentication authentication) {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        jobApplicationService.updateStatusOrScore(
                updateDTO.getApplicationId(),
                updateDTO.getNewStatus(),
                updateDTO.getNewScore(),
                principal
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/company-applications/{jobVacancyId}")
    public ResponseEntity<Page<JobApplicationResponseDTO>> getApplicationsByJob(
            @PathVariable Long jobVacancyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Authentication authentication) {

        Long companyId = ((UserPrincipal) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(jobApplicationService.getApplicationsByJobVacancy(jobVacancyId, companyId, page, size));
    }
}
