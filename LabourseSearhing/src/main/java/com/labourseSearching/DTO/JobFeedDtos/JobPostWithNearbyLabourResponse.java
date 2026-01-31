package com.labourseSearching.DTO.JobFeedDtos;

import lombok.Data;

import java.util.List;

@Data
public class JobPostWithNearbyLabourResponse {

    private jobResponceDto job;
    private List<NearbyLabourDto> nearbyLabours;

}