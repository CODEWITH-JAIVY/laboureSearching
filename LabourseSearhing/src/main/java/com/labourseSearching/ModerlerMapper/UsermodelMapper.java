package com.labourseSearching.ModerlerMapper;

import com.labourseSearching.DTO.AddressDtos.AddressDto;
import com.labourseSearching.DTO.ProfileDtos.LaboureProfileDto;
import com.labourseSearching.DTO.UserDto.EmailAuthSingInRequestDto;
import com.labourseSearching.DTO.UserDto.UserEmailLoninResponseDto;
import com.labourseSearching.DTO.UserDto.UserMyProfileDto;
import com.labourseSearching.DTO.UserDto.UserTypeSelectResponceDto;
import com.labourseSearching.Entity.ENUM.AuthProvider;
import com.labourseSearching.Entity.ENUM.UserType;
import com.labourseSearching.Entity.Users.User;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class UsermodelMapper {

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UsermodelMapper(
            PasswordEncoder passwordEncoder,
            ModelMapper modelMapper
    ) {
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    // ================= SIGNUP DTO → ENTITY =================
    public User toSingInUser(@Valid EmailAuthSingInRequestDto request) {

        // ModelMapper already maps: name, email, mobile
        User user = modelMapper.map(request, User.class);
        user.setName(request.getName());

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthProvider(AuthProvider.EMAIL);
        user.setCreatedAt(LocalDate.now());

        return user;
    }

    // ================= ENTITY → RESPONSE =================
    public UserEmailLoninResponseDto toResponce(
            User user,
            String token ,
            boolean profileExit
    ) {
        UserEmailLoninResponseDto dto = new UserEmailLoninResponseDto();

        dto.setUserid(user.getId());
        dto.setUsername(user.getName());
        dto.setEmail(user.getEmail());
        dto.setToken(token);
        dto.setProfileExists(profileExit);

        return dto;
    }


    // ================= PROFILE =================
    public UserMyProfileDto toResponceMyprofile(User user) {

        UserMyProfileDto dto = new UserMyProfileDto();

        LaboureProfileDto profileDto = new LaboureProfileDto();
        profileDto.setName(user.getName());   // ✅ NOW CORRECT
        profileDto.setEmail(user.getEmail());
        profileDto.setMobile(user.getMobile());
        profileDto.setProfileImageUrl(user.getProfileImageUrl());

        if (user.getLabourProfile() != null) {
            profileDto.setLabourType(user.getLabourProfile().getLabourType());
            profileDto.setEmploymentType(user.getLabourProfile().getEmploymentType());
            profileDto.setWorkingHours(user.getLabourProfile().getWorkingHours());
            profileDto.setEducationList(user.getLabourProfile().getEducationList());
        }

        dto.setProfileDto(profileDto);

        if (user.getAddresses() != null && !user.getAddresses().isEmpty()) {
            AddressDto addressDto = new AddressDto();
            modelMapper.map(user.getAddresses().get(0), addressDto);
            dto.setAddressDto(addressDto);
        }

        return dto;
    }

    public UserTypeSelectResponceDto userRoleSetAtLoginOrSingInFirstTime(User user) {

        boolean profileExists = false;

        if (user.getUserType() == UserType.LABOUR) {
            profileExists = user.getLabourProfile() != null;
        }

        if (user.getUserType() == UserType.CUSTOMER) {
            profileExists = user.getCustomerProfile() != null;
        }

        UserTypeSelectResponceDto dto = new UserTypeSelectResponceDto();
        dto.setUserType(user.getUserType());
        dto.setProfileExists(profileExists);

        return dto;
    }


}