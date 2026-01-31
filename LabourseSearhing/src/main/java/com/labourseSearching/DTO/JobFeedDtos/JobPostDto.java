package com.labourseSearching.DTO.JobFeedDtos;

import com.labourseSearching.Entity.ENUM.LabourType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JobPostDto {

    //    private User customer;
    private String workimgeUrl; // must require
    private String workvideoUrl;
    private String WorkDescriptopm; // must  Require
    private LabourType labourType; // must need
    private int wagePerDay; // must need to fill this
    private LocalDate workDate;
    private boolean active; // true = open, false = closed
}