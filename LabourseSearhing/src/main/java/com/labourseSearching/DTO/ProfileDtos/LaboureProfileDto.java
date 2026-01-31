package com.labourseSearching.DTO.ProfileDtos;

import com.labourseSearching.Entity.ENUM.EmploymentType;
import com.labourseSearching.Entity.ENUM.LabourType;
import com.labourseSearching.Entity.EducationQualification.Education;
import lombok.Data;

import java.util.List;

@Data
public class LaboureProfileDto {

    private String name;
    private String email;
    private String mobile;
    private String profileImageUrl;
    private LabourType labourType ;
    private EmploymentType employmentType ;
    private boolean availableToday;
    private String workingHours;   // "9 AM - 6 PM"
    private String about;          // bio
    private List<Education> educationList ;
}