package com.mtech.addmissions.dto.request;

import java.util.List;

import com.mtech.addmissions.model.Branch;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollegeDTO {
    private Long id;
    private String collegeName;
    private String collegeAddress;
    private String collegeCode;
    // private String district;
    private String universityName;
    private String collegeType;
    private List<Branch> branches;

}
