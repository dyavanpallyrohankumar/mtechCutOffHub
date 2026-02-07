package com.mtech.addmissions.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchDTO {
    private Long id;
    private String branchName;
    private String collegeProgramCode;
}
