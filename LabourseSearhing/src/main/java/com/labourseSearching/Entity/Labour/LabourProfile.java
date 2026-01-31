package com.labourseSearching.Entity.Labour;

import com.labourseSearching.Entity.ENUM.EmploymentType;
import com.labourseSearching.Entity.ENUM.LabourType;
import com.labourseSearching.Entity.EducationQualification.Education;
import com.labourseSearching.Entity.LocationLiveTracing.LiveLocation;
import com.labourseSearching.Entity.UserAddresh.Address;
import com.labourseSearching.Entity.Users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LabourProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Enumerated(EnumType.STRING)
    private LabourType labourType;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @OneToOne(mappedBy = "labourProfile")
    private Address address;


    private boolean availableToday;

    private String workingHours;


    private String about;

    @OneToMany(
            mappedBy = "labourProfile",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private List<Education> educationList;



   //================================================================
    //          WHEN THE USER WILL SHARE THERE CURRENET LOCATION
    // ===============================================================
   @Embedded
   private LiveLocation liveLocation;


}