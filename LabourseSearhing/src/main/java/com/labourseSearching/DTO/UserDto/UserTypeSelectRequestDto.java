package com.labourseSearching.DTO.UserDto;

import com.labourseSearching.Entity.ENUM.UserType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTypeSelectRequestDto {

    @NotNull
    private UserType userType; // LABOUR / CUSTOMER
//    private boolean profileExists;

    // Optional: future use
//    private Long profileId;
}