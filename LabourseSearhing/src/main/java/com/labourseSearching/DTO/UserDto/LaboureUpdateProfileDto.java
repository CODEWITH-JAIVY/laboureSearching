package com.labourseSearching.DTO.UserDto;

import com.labourseSearching.Entity.UserAddresh.Address;
import lombok.Data;

@Data
public class LaboureUpdateProfileDto {

    private String name;
    private String email;
    private String mobile;

    // profile related
    private String skills;
    private Integer experience;

    // image (optional)
    private Long workPhotoId;

    // address
    private Address address;


    private String workingHours;   // "9 AM - 6 PM"
    private String about;

}