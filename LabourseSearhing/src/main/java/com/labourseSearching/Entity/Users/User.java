package com.labourseSearching.Entity.Users;

import com.labourseSearching.Entity.Customer.CustomerProfile;
import com.labourseSearching.Entity.ENUM.AuthProvider;
import com.labourseSearching.Entity.ENUM.UserType;
import com.labourseSearching.Entity.JobPost.JobPost;
import com.labourseSearching.Entity.JobResponseByLobourse.JobResponse;
import com.labourseSearching.Entity.Labour.LabourProfile;
import com.labourseSearching.Entity.LocationLiveTracing.LiveLocation;
import com.labourseSearching.Entity.UserAddresh.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // VALID MOBILE NUMBER
    private String mobile;

    private String genderPreference;
    // VALID EMAIL
    private String email;

    @Column(length = 700)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private LocalDate createdAt;

    // cheak valid passord must include UPPERCASE  , LOWERCASE , NUMBER OR SING
    private String password  ;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Address> addresses;


    // ================================================================
    //          for the live location or to search the near  user
    //==================================================================


    // ================================================================
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private LabourProfile labourProfile;

     @OneToOne (mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true )
     private CustomerProfile customerProfile  ;

    @OneToMany(mappedBy = "labour" , fetch = FetchType.LAZY)  // remove N+1 problem
    private List<JobResponse> jobResponses;



     @OneToMany(mappedBy = "customer" , fetch = FetchType.LAZY)
     private List<JobPost> jobPosts;


    @Embedded
    private LiveLocation liveLocation;

}