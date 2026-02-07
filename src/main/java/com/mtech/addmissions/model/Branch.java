package com.mtech.addmissions.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String branchName;
    private String collegeProgramCode;

    @ManyToOne
    @JoinColumn(name = "college_id")
    private College college;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<StudentAllotment> allotments;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private List<CutoffSummary> cutoffs;

    public Branch(String branchName, String collegeProgramCode) {
        this.branchName = branchName;
        this.collegeProgramCode = collegeProgramCode;
    }

    @Override
    public String toString() {
        return "Branch [id=" + id + ", branchName=" + branchName + ", collegeProgramCode=" + collegeProgramCode
                + ", allotments="
                + allotments + ", cutoffs=" + cutoffs + "]";
    }

}
