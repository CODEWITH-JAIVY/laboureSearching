package com.labourseSearching.Repository.Laboure;

import com.labourseSearching.Entity.Labour.LabourProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabourProfileRepository
        extends JpaRepository<LabourProfile, Long> {

    List<LabourProfile> findByIdIn(List<Long> labourIds);


}