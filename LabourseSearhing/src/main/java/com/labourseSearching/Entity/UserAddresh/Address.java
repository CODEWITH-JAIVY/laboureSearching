package com.labourseSearching.Entity.UserAddresh;

import com.labourseSearching.Entity.Labour.LabourProfile;
import com.labourseSearching.Entity.Users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String local;
    private String landmark;
    private String postalCode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "labour_profile_id")
    private LabourProfile labourProfile;


    private String liveLocationUrl   ;

}