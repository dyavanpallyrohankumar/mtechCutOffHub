package com.mtech.addmissions.dto.response;

import lombok.*;
import java.util.List;

import com.mtech.addmissions.dto.request.BranchDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollegeWithBranchesDTO {
    private Long id;
    private String collegeName;
    private String collegeAddress;
    private String collegeCode;
    // private String district;
    private String universityName;
    private String collegeType;

    private List<BranchDTO> branches;
}
