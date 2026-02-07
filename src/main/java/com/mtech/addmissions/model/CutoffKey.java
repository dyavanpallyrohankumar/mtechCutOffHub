package com.mtech.addmissions.model;

import com.mtech.addmissions.enums.Exam;
import com.mtech.addmissions.enums.Gender;

import lombok.*;

// @Getter
// @AllArgsConstructor
// @EqualsAndHashCode
// public class CutoffKey {

//     private final College college;
//     private final Branch branch;
//     private final String category;
//     private final Gender gender;
//     private final Exam exam;
//     private final String phase;
//     private final Integer year;
// }

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CutoffKey {

    private final String programCode; // college+branch unique
    private final String category;
    private final Gender gender;
    private final Exam exam;
    private final String phase;
    private final Integer year;
}
