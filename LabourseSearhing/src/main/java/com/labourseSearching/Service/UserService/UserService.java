package com.labourseSearching.Service.UserService;

import com.labourseSearching.DTO.UserDto.*;
import com.labourseSearching.Entity.ENUM.UserType;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {


    UserEmailLoninResponseDto emailAuthSingIn(@Valid EmailAuthSingInRequestDto request);

    UserEmailLoninResponseDto emailAuthLogin(@Valid EmailAuthLonginRequestDto request);


    UserAuthStateDto showMyProfile(long id);

    String uploadProfilePhoto(Long id, MultipartFile file);
    UserTypeSelectResponceDto setUserType(long userId, UserType userType);

}