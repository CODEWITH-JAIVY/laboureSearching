package com.labourseSearching.ModerlerMapper;

import com.labourseSearching.DTO.UserDto.LaboureUpdateProfileDto;
import com.labourseSearching.Entity.Users.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LaboureUpdateProfileModelMapper {

    private final ModelMapper modelMapper;

    public LaboureUpdateProfileModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // DTO → EXISTING ENTITY
    public void mapToEntity(
            LaboureUpdateProfileDto dto,
            User user
    ) {
        modelMapper.map(dto, user);
    }

    // ENTITY → RESPONSE DTO
    public LaboureUpdateProfileDto toDto(User user) {

        return modelMapper.map(user, LaboureUpdateProfileDto.class);
    }
}