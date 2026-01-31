package com.labourseSearching.ModerlerMapper;

import com.labourseSearching.DTO.JobFeedDtos.JobPostDto;
import com.labourseSearching.DTO.JobFeedDtos.jobResponceDto;
import com.labourseSearching.Entity.JobPost.JobPost;
import com.labourseSearching.Entity.Users.User;
import org.springframework.stereotype.Component;

@Component
public class JobPostModelMapper {

    public JobPost toEntity(JobPostDto dto, User customer) {
        JobPost job = new JobPost();
        job.setCustomer(customer);
        job.setWorkimgeUrl(dto.getWorkimgeUrl());
        job.setWorkvideoUrl(dto.getWorkvideoUrl());
        job.setWorkDescriptopm(dto.getWorkDescriptopm());
        job.setLabourType(dto.getLabourType());
        job.setWagePerDay(dto.getWagePerDay());
        job.setWorkDate(dto.getWorkDate());
        job.setActive(true);
        return job;
    }

    public jobResponceDto toResponse(JobPost job) {
        jobResponceDto dto = new jobResponceDto();
        dto.setJobId(job.getId());
        dto.setWorkDescriptopm(job.getWorkDescriptopm());
        dto.setLabourType(job.getLabourType());
        return dto;
    }
}