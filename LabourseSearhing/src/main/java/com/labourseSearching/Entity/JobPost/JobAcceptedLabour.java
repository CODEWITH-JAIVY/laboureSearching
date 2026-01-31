package com.labourseSearching.Entity.JobPost;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor

@Table(name = "job_accepted_labours")
public class JobAcceptedLabour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;
    private Long labourId;
    private String labourType;

    public JobAcceptedLabour() {
    }

    public JobAcceptedLabour(Long jobId, Long labourId, String labourType) {
        this.jobId = jobId;
        this.labourId = labourId;
        this.labourType = labourType;
    }

    public Long getJobId() {
        return jobId;
    }

    public Long getLabourId() {
        return labourId;
    }

    public String getLabourType() {
        return labourType;
    }
}