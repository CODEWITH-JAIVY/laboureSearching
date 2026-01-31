package com.labourseSearching.DTO.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailAuthSingInRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Mobile is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Mobile must be 10 digits"
    )
    private String mobile;

    @NotBlank(message = "Email required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 8, message = "Min 8 chars")
    private String password;


}