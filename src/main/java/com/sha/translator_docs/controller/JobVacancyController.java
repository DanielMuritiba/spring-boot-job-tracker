package com.sha.translator_docs.controller;

import com.sha.translator_docs.DTO.JobVacancyDTO.JobVacancyRequestDTO;
import com.sha.translator_docs.DTO.JobVacancyDTO.JobVacancyResponseDTO;
import com.sha.translator_docs.model.User;
import com.sha.translator_docs.security.UserPrincipal;
import com.sha.translator_docs.service.JobVacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/job-vacancy")
public class JobVacancyController {

    @Autowired
    private JobVacancyService jobVacancyService;

    @PostMapping
    public ResponseEntity<JobVacancyResponseDTO> saveJobVacancy(
            @RequestBody JobVacancyRequestDTO jobVacancyDTO,
            Authentication authentication) {
        Long companyId = ((UserPrincipal) authentication.getPrincipal()).getId();
        JobVacancyResponseDTO response = jobVacancyService.saveJobVacancy(jobVacancyDTO, companyId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{jobVacancyId}")
    public ResponseEntity<?> deleteJobVacancy(
            @PathVariable Long jobVacancyId,
            Authentication authentication) {

        Long companyId = ((UserPrincipal) authentication.getPrincipal()).getId();
        jobVacancyService.deleteJobVacancy(jobVacancyId, companyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<JobVacancyResponseDTO>> getAllJobVacancy() {
        return ResponseEntity.ok(jobVacancyService.findAllJobVacancy());
    }

    @GetMapping("/company")
    public ResponseEntity<?> getCompanyJobVacancy(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long companyId = userPrincipal.getId();

        return ResponseEntity.ok(jobVacancyService.findByCompanyPaginated(companyId, page, size));
    }
}
