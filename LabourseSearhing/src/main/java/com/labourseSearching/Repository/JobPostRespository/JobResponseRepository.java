package com.labourseSearching.Repository.JobPostRespository;

import com.labourseSearching.Entity.JobResponseByLobourse.JobResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobResponseRepository extends JpaRepository<JobResponse, Long> {
}