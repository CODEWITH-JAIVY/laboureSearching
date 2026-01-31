package com.labourseSearching.Service.JobPostService;

import com.labourseSearching.DTO.JobFeedDtos.JobPostDto;
import com.labourseSearching.DTO.JobFeedDtos.JobPostWithNearbyLabourResponse;
import com.labourseSearching.DTO.JobFeedDtos.jobResponceDto;

public interface JobService {

    jobResponceDto postJobByCustomer(
            long userId,
            JobPostDto jobPostDto
    );

    JobPostWithNearbyLabourResponse postJobWithNearbyLabours(
            long userId,
            JobPostDto jobPostDto,
            int page,
            int size
    );
}