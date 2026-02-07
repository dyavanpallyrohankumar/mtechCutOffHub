package com.mtech.addmissions.dto.response;

import com.mtech.addmissions.enums.Exam;
import com.mtech.addmissions.enums.Gender;
import com.mtech.addmissions.enums.Region;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAllotmentDTO {
    private Long id;
    private String studentName;
    private String category;
    private Gender gender;
    private Region region;
    private Exam exam;
    private String scoreOrPercentile;
    private Integer rank;
    private String allotedCategory;
    private String phase;
    private Integer year;
    private String collegeProgramCode;
    private Long branchId;
}
