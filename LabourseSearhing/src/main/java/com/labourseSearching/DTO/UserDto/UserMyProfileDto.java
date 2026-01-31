package com.labourseSearching.DTO.UserDto;


import com.labourseSearching.DTO.AddressDtos.AddressDto;
import com.labourseSearching.DTO.Labourse.LabourWorkPhotoDto;
import com.labourseSearching.DTO.ProfileDtos.LaboureProfileDto;
import lombok.Data;

@Data

public class UserMyProfileDto {

    private LaboureProfileDto profileDto ;

    private LabourWorkPhotoDto labourWorkPhotoDto  ;
    
    private AddressDto addressDto  ;

    //  here we can set a useravailbe direct but no
    // client will be   use for this to redio buttom to set today  am vaileb  os there will be aother api

}