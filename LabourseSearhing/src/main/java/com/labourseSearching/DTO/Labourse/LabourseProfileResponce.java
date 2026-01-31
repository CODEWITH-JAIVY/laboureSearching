package com.labourseSearching.DTO.Labourse;

import com.labourseSearching.DTO.AddressDtos.AddressDto;
import com.labourseSearching.DTO.ProfileDtos.LaboureProfileDto;
import lombok.Data;

@Data
public class LabourseProfileResponce {

    private LaboureProfileDto profile;
    private AddressDto address;
}