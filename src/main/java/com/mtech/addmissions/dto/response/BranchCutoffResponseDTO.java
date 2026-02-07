package com.mtech.addmissions.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchCutoffResponseDTO {
    private Long collegeId;
    private String collegeName;
    private Long branchId;
    private String branchName;

    private List<CutoffSummaryDTO> cutoffs;
}

// GET /api/cutoffs/college/{collegeId}/branch/{branchId}