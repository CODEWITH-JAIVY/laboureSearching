package com.labourseSearching.Service.LaboureService;

import com.labourseSearching.DTO.AddressDtos.LocationUpdateDto;
import com.labourseSearching.DTO.JobFeedDtos.JobFeedDto;
import com.labourseSearching.DTO.Labourse.LabourseProfileCreateDto;
import com.labourseSearching.DTO.Labourse.LabourseProfileResponce;
import com.labourseSearching.DTO.UserDto.LaboureUpdateProfileDto;

import java.util.List;

public interface LabourseService {

    LaboureUpdateProfileDto updateProfile(long laboureId, LaboureUpdateProfileDto laboureUpdateProfileDto);


    // @Nullable LabourseProfileResponce createProfile(UserPrincipal user, LabourseProfileCreateDto dto);

    LabourseProfileResponce createProfile(
            long userId,
            LabourseProfileCreateDto dto
    );

    LocationUpdateDto updateLiveLocation(long userid, LocationUpdateDto liveDto);


     List<JobFeedDto> getNearbyJobsForLabourer(Long id, int page, int size);





}