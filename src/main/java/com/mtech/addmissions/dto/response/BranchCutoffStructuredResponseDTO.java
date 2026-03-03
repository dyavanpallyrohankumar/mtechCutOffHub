package com.mtech.addmissions.dto.response;

import java.util.List;
import com.mtech.addmissions.enums.Exam;
import com.mtech.addmissions.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BranchCutoffStructuredResponseDTO {

    private Long collegeId;
    private Long branchId;

    private List<YearDTO> years;

    @Data
    @AllArgsConstructor
    public static class YearDTO {
        private Integer year;
        private List<PhaseDTO> phases;
    }

    @Data
    @AllArgsConstructor
    public static class PhaseDTO {
        private String phase;
        private List<CategoryDTO> categories;
    }

    @Data
    @AllArgsConstructor
    public static class CategoryDTO {
        private String category;
        private List<ExamDTO> exams;
    }

    @Data
    @AllArgsConstructor
    public static class ExamDTO {
        private Exam exam;
        private List<GenderCutoffDTO> genders;
    }

    @Data
    @AllArgsConstructor
    public static class GenderCutoffDTO {
        private Gender gender;
        private Integer startRank;
        private Integer endRank;
        private Double startPercentile;
        private Double endPercentile;
    }
}