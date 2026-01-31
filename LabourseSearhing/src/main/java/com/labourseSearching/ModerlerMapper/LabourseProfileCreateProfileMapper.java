package com.labourseSearching.ModerlerMapper;

import com.labourseSearching.DTO.AddressDtos.AddressDto;
import com.labourseSearching.DTO.Labourse.LabourseProfileCreateDto;
import com.labourseSearching.DTO.Labourse.LabourseProfileResponce;
import com.labourseSearching.DTO.ProfileDtos.LaboureProfileDto;
import com.labourseSearching.Entity.Labour.LabourProfile;
import com.labourseSearching.Entity.UserAddresh.Address;
import com.labourseSearching.Entity.Users.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LabourseProfileCreateProfileMapper {

    private final ModelMapper modelMapper;

    public LabourseProfileCreateProfileMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void mapToEntity(LabourseProfileCreateDto dto, User user) {

        LabourProfile profile = user.getLabourProfile();
        if (profile == null) {
            profile = new LabourProfile();
            profile.setUser(user);
            user.setLabourProfile(profile);
        }

        if (dto.getProfile() != null) {
            modelMapper.map(dto.getProfile(), profile);
        }

        if (dto.getAddress() != null) {
            Address address = new Address();
            modelMapper.map(dto.getAddress(), address);

            address.setUser(user);
            address.setLabourProfile(profile);

            profile.setAddress(address);

            if (user.getAddresses() == null) {
                user.setAddresses(new ArrayList<>());
            }
            user.getAddresses().add(address);
        }
    }

    public LabourseProfileResponce toResponse(User user) {

        LabourseProfileResponce response = new LabourseProfileResponce();

        if (user.getLabourProfile() != null) {
            LaboureProfileDto profileDto = new LaboureProfileDto();
            modelMapper.map(user.getLabourProfile(), profileDto);
            response.setProfile(profileDto);
        }

        if (user.getLabourProfile() != null &&
                user.getLabourProfile().getAddress() != null) {

            AddressDto addressDto = new AddressDto();
            modelMapper.map(user.getLabourProfile().getAddress(), addressDto);
            response.setAddress(addressDto);
        }

        return response;
    }
}