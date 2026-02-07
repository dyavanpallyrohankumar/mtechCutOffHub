package com.mtech.addmissions.model;

import com.mtech.addmissions.enums.Exam;
import com.mtech.addmissions.enums.Gender;
import com.mtech.addmissions.enums.Region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentAllotment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "college_id")
    private College college;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Enumerated(EnumType.STRING)
    private Exam exam;

    @Enumerated(EnumType.STRING)
    private Region region;

    private String studentName;
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    private String allotedCategory;

    private String phase;
    private String scoreOrPercentile;

    private Integer rank;

    private Integer year;// default now 2025
    private String collegeProgramCode;

    public StudentAllotment(College college, Branch branch, String studentName,
            String catogory, String allotedCategory, String phase, String scoreOrPercentile,
            Integer rank, Integer year) {
        this.college = college;
        this.branch = branch;
        this.studentName = studentName;
        this.category = catogory;
        this.allotedCategory = allotedCategory;
        this.phase = phase;
        this.scoreOrPercentile = scoreOrPercentile;
        this.rank = rank;
        this.year = year;
    }

    @Override
    public String toString() {
        return "StudentAllotment [id=" + id + ", exam=" + exam + ", region=" + region + ", studentName=" + studentName
                + ", category=" + category + ", gender=" + gender + ", allotedCategory=" + allotedCategory + ", phase="
                + phase + ", scoreOrPercentile=" + scoreOrPercentile + ", rank=" + rank + ", year=" + year
                + ", collegeProgramCode=" + collegeProgramCode + "]";
    }

}
