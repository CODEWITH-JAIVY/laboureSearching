package com.labourseSearching.Entity.Labour;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabourWorkPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id  ;

    @ManyToOne
    private LabourProfile labourProfile  ;


    private String imageUrl  ;


    private LocalDate uploadedAt;

}