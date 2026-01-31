package com.labourseSearching.DTO.JobFeedDtos;

import com.labourseSearching.Entity.ENUM.LabourType;
import com.labourseSearching.Entity.JobPost.JobPost;
import lombok.Data;

@Data
public class JobFeedDto {

    private Long jobId;
    private String description;
    private LabourType labourType;
    private Double distanceKm;

    public static JobFeedDto from(JobPost job, Double distance) {
        JobFeedDto dto = new JobFeedDto();
        dto.jobId = job.getId();
        dto.description = job.getWorkDescriptopm();
        dto.labourType = job.getLabourType();
        dto.distanceKm = distance;
        return dto;
    }
}