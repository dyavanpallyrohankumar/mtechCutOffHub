package com.mtech.addmissions.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mtech.addmissions.enums.Exam;
import com.mtech.addmissions.enums.Gender;
import com.mtech.addmissions.model.Branch;
import com.mtech.addmissions.model.College;
import com.mtech.addmissions.model.CutoffSummary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CutoffRepository extends JpaRepository<CutoffSummary, Long> {

    @Query("""
                SELECT c
                FROM CutoffSummary c
                JOIN c.college col
                JOIN c.branch br
                WHERE col.collegeCode = :collegeCode
                AND br.collegeProgramCode = :branchCode
            """)
    List<CutoffSummary> findCutoffsByCollegeCodeAndBranchCode(
            @Param("collegeCode") String collegeCode,
            @Param("branchCode") String branchCode);

    // List<CutoffSummary> findAllByCollegeAndBranch(College college, Branch
    // branch);

    List<CutoffSummary> findAllByCollege(College college);

    Optional<CutoffSummary> findByCollegeAndBranchAndCategoryAndGenderAndExamAndPhaseAndYear(College id, Branch id2,
            String category, Gender gender, Exam exam, String phase, Integer year);

}
