package com.sha.translator_docs.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "jobApplication")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_job_vacancy", nullable = false)
    private JobVacancy jobVacancy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_application", nullable = false)
    private StatusApplication statusApplication;

    @Column(name = "user_username", nullable = false, length = 100)
    private String userUsername;

    @Column(name = "user_skills", nullable = false, length = 100)
    private String skills;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "user_description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "user_score", nullable = true)
    private Integer score;
}
