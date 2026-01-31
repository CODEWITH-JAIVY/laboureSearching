package com.labourseSearching.Repository.JobPostRespository;


import com.labourseSearching.Entity.JobPost.JobAcceptedLabour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobAcceptedLabourRepository
        extends JpaRepository<JobAcceptedLabour, Long> {

    List<JobAcceptedLabour> findByJobId(Long jobId);
}