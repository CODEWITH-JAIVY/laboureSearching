package com.labourseSearching.DTO.UserDto;

import lombok.Data;

@Data
public class UserEmailLoninResponseDto {

    private long  userid ;
    private String username  ;
    private String email  ;
    private boolean profileExists;
    private String token ;  // jwt token

}