package com.labourseSearching.DTO.Labourse;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LabourWorkPhotoDto {


    private String imageUrl  ;


    private LocalDate uploadedAt;
}