package com.mtech.addmissions.model;

import com.mtech.addmissions.enums.Exam;
import com.mtech.addmissions.enums.Gender;

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
public class CutoffSummary {
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

    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    private String phase;
    private Integer year;

    private Integer startRank;
    private Integer endRank;

    private Double startPercentile;
    private Double endPercentile;

    @Override
    public String toString() {
        return "CutoffSummary [id=" + id + ", exam=" + exam + ", category=" + category + ", gender=" + gender
                + ", phase=" + phase + ", year=" + year + ", startRank=" + startRank + ", endRank=" + endRank
                + ", startPercentile=" + startPercentile + ", endPercentile=" + endPercentile + "]";
    }

}
