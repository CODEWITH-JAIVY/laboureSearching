package com.labourseSearching.DTO.UserDto;

import com.labourseSearching.Entity.ENUM.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthStateDto {
    private Long userId;
    private UserType userType;
    private boolean profileExists;
}