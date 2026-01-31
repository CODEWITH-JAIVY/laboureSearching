package com.labourseSearching.Entity.Customer;

import com.labourseSearching.Entity.LocationLiveTracing.LiveLocation;
import com.labourseSearching.Entity.UserAddresh.Address;
import com.labourseSearching.Entity.Users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id  ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    private Address addresh  ;

    @Embedded
    private LiveLocation liveLocation;

}