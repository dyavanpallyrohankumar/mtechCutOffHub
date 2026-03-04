package com.mtech.addmissions.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.mtech.addmissions.enums.Exam;
import com.mtech.addmissions.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cutoff_summary", uniqueConstraints = @UniqueConstraint(name = "uk_cutoff_logical_key", columnNames = {
                "college_id",
                "branch_id",
                "category",
                "gender",
                "exam",
                "phase",
                "year"
}), indexes = {
                @Index(name = "idx_cutoff_college", columnList = "college_id"),
                @Index(name = "idx_cutoff_branch", columnList = "branch_id"),
                @Index(name = "idx_cutoff_year", columnList = "year")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CutoffSummary {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // =========================
        // RELATIONSHIPS
        // =========================

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "college_id", nullable = false)
        private College college;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "branch_id", nullable = false)
        private Branch branch;

        // =========================
        // LOGICAL CUT-OFF KEY
        // =========================

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Exam exam;

        @Column(nullable = false)
        private String category;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Gender gender;

        @Column(nullable = false)
        private String phase;

        @Column(nullable = false)
        private Integer year;

        // =========================
        // ANALYTICS VALUES
        // =========================

        private Integer startRank;
        private Integer endRank;

        private Double startPercentile;
        private Double endPercentile;

        @CreationTimestamp
        private LocalDateTime createdAt;

        @UpdateTimestamp
        private LocalDateTime updatedAt;

        // =========================
        // SAFE toString
        // =========================

        @Override
        public String toString() {
                return "CutoffSummary{" +
                                "id=" + id +
                                ", exam=" + exam +
                                ", category='" + category + '\'' +
                                ", gender=" + gender +
                                ", phase='" + phase + '\'' +
                                ", year=" + year +
                                ", startRank=" + startRank +
                                ", endRank=" + endRank +
                                ", startPercentile=" + startPercentile +
                                ", endPercentile=" + endPercentile +
                                '}';
        }
}