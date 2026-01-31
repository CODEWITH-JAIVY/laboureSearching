package com.labourseSearching.Entity.JobPost;

import com.labourseSearching.Entity.ENUM.LabourType;
import com.labourseSearching.Entity.Users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User customer;

    private String workimgeUrl;  /// WORK IMAGE WILL BE UPLOAD BY THE CUSTOMER

    private String workvideoUrl;

    private String WorkDescriptopm; // "Aaj Ghar ka saman uthana hai"


    private LabourType labourType; //  MASON,
//    PAINTER,
//    HELPER,
//    ELECTRICIAN,
//    PLUMBER,
//    CARPENTER,
//    WELDER ,
//    LOADER,         // Loading / Unloading
//    CLEANER,

    private int wagePerDay;

    private LocalDate workDate;
    private boolean active; // true = open, false = closed


}