package com.labourseSearching.DTO.Labourse;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class jobAcceptedLabourDto {

    private Long labourId;
    private String name;
    private String labourType;
    private String mobile  ;

}