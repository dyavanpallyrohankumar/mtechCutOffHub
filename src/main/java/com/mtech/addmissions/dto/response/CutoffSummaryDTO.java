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
    private String category;
    private Gender gender;
    private Exam exam;
    private String phase;
    private Integer year;

    private Integer startRank;
    private Integer endRank;
    private Double startPercentile;
    private Double endPercentile;

}
