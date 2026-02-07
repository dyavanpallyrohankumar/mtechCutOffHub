package com.mtech.addmissions.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollegeListDTO {
    private Long id;
    private String collegeName;
    private String collegeType;
    private String universityName;

    // private String district;
}
