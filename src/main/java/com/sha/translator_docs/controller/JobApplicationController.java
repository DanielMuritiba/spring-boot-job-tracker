package com.sha.translator_docs.controller;

import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationCsvDTO;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationResponseDTO;
import com.sha.translator_docs.DTO.JobApplicationDTO.JobApplicationUpdateRequestDTO;
import com.sha.translator_docs.model.JobApplication;
import com.sha.translator_docs.model.User;
import com.sha.translator_docs.security.UserPrincipal;
import com.sha.translator_docs.service.JobApplicationService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<JobApplication> applyToJob(
            @PathVariable Long jobVacancyId,
            @RequestBody JobApplicationCsvDTO csvData,
            Authentication authentication) {

        Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
        User user = new User();
        user.setId(userId);

        JobApplication application = jobApplicationService.applyToJob(jobVacancyId, user, csvData);
        return new ResponseEntity<>(application, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<JobApplicationResponseDTO>> getAllUserApplications(Authentication authentication) {
        Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(jobApplicationService.getAllApplicationsByUser(userId));
    }


    @PutMapping("/profile")
    public ResponseEntity<Void> updateStatusOrScore(
            @RequestBody JobApplicationUpdateRequestDTO updateDTO,
            Authentication authentication) {

        Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
        User user = new User();
        user.setId(userId);

        jobApplicationService.updateStatusOrScore(
                updateDTO.getApplicationId(),
                updateDTO.getNewStatus(),
                updateDTO.getNewScore(),
                user
        );

        return ResponseEntity.ok().build();
    }
}
