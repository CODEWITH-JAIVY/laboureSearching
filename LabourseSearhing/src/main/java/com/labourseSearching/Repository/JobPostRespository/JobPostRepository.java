package com.labourseSearching.Repository.JobPostRespository;

import com.labourseSearching.Entity.JobPost.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    List<JobPost> findByIdInAndActiveTrue(List<Long> ids);
}