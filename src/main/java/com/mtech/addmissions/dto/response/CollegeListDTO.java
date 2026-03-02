package com.mtech.addmissions.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollegeListDTO {
    private String collegeName;
    private String collegeAddress;
    private String collegeCode;
    // private String district;
    private String universityName;
    private String collegeType;

}
