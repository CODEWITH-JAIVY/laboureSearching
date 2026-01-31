package com.labourseSearching.Entity.EducationQualification;

import com.labourseSearching.Entity.Labour.LabourProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "labour_profile_id", nullable = false)
    private LabourProfile labourProfile;   // OWNING SIDE

    private String qualification;   // ITI, Diploma, B.Tech
    private String instituteName;
    private int passingYear;

    private String certificateUrl;  // image / pdf

    private boolean verified;        // future use


}