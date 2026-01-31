package com.labourseSearching.DTO.JobFeedDtos;


import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NearbyLabourDto {

    private Long labourProfileId;
    private String name;
    private String labourType;
    private double distanceKm;
    private boolean availableToday;
}