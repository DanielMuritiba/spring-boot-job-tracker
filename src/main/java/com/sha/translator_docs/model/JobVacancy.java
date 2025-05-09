package com.sha.translator_docs.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "jobVacancy")
public class JobVacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_company", nullable = false)
    private User company;

    @Column(name = "job_name", nullable = false, length = 100)
    private String jobName;

    @Column(name = "required_skills", nullable = false, length = 100)
    private String requiredSkills;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

}
