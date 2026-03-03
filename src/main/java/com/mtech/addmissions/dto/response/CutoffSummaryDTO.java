package com.mtech.addmissions.dto.response;

import com.mtech.addmissions.enums.Exam;
import com.mtech.addmissions.enums.Gender;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CutoffSummaryDTO {
       private Integer year;

    private String phase;
    private String category;


    private Exam exam;


 private Gender gender;

    private Integer startRank;
    private Integer endRank;
    private Double startPercentile;
    private Double endPercentile;

}
