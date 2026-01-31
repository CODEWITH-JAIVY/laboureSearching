package com.labourseSearching.Service.CustomerService;

import com.labourseSearching.DTO.AddressDtos.LocationUpdateDto;
import com.labourseSearching.DTO.CustomerDto.CustomerProfileDto;
import com.labourseSearching.DTO.CustomerDto.CustomerProfileResponceDto;

public interface CustomerService {

    CustomerProfileResponceDto createProfile(
            long userId,
            CustomerProfileDto dto
    );

    LocationUpdateDto updateLiveLocation(
            long userId,
            LocationUpdateDto liveDto
    );
}