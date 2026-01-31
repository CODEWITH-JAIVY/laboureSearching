package com.labourseSearching.DTO.JobFeedDtos;

import com.labourseSearching.Entity.ENUM.LabourType;
import lombok.Data;

@Data
public class jobResponceDto {
    private Long jobId;
    private String description;
    private LabourType labourType;
    private String WorkDescriptopm;
}