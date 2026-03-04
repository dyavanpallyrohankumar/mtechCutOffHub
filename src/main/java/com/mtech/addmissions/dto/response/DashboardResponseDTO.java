package com.mtech.addmissions.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardResponseDTO {

    private DashboardSummaryDto summary;
    private List<YearlyTrendDto> yearlyTrends;
    private List<ActivityDto> activities;
    private DataHealthDto dataHealth;

    @Data
    public static class DashboardSummaryDto {

        private Integer totalColleges;
        private Integer totalBranches;
        private Integer totalAllotments;
        private Integer activeYears;
        private String lastImportDate;
        private String systemVersion;

    }

    @Data
    public static class YearlyTrendDto {

        private Integer year;
        private Integer count;

    }

    @Data
    public static class ActivityDto {

        private Long id;
        private String type;
        private String message;
        private String createdAt;

    }

    @Data
    public static class DataHealthDto {

        private Integer missingCategories;
        private Integer duplicateRecords;
        private Integer unmappedBranches;
        private Integer unverifiedAllotments;

    }

}
