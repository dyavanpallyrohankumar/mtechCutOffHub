package com.mtech.addmissions.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mtech.addmissions.model.Branch;
import com.mtech.addmissions.model.College;
import com.mtech.addmissions.model.StudentAllotment;

public interface StudentAllotmentRepository extends JpaRepository<StudentAllotment, Long> {
    List<StudentAllotment> findByCollegeAndBranch(College college, Branch branch);

    List<StudentAllotment> findByCollege(College college);

    List<StudentAllotment> findByBranch(Branch branch);

    /*
     * Yearly allotment count for dashboard graph
     */
    @Query("""
                SELECT a.year, COUNT(a)
                FROM StudentAllotment a
                GROUP BY a.year
                ORDER BY a.year
            """)
    List<Object[]> getYearlyAllotments();

    /*
     * Missing category records
     */
    @Query("""
                SELECT COUNT(a)
                FROM StudentAllotment a
                WHERE a.category IS NULL OR a.category = ''
            """)
    int countMissingCategories();

    /*
     * Distinct active years
     */
    @Query("""
                SELECT COUNT(DISTINCT a.year)
                FROM StudentAllotment a
            """)
    int countDistinctYears();

    /*
     * Duplicate records (same college + branch + rank + year)
     */
    @Query("""
                SELECT COUNT(a)
                FROM StudentAllotment a
                WHERE (a.college, a.branch, a.rank, a.year) IN (
                    SELECT b.college, b.branch, b.rank, b.year
                    FROM StudentAllotment b
                    GROUP BY b.college, b.branch, b.rank, b.year
                    HAVING COUNT(b) > 1
                )
            """)
    Integer countDuplicateRecords();

    /*
     * Records where branch mapping failed
     */
    @Query("""
                SELECT COUNT(a)
                FROM StudentAllotment a
                WHERE a.branch IS NULL
            """)
    Integer countUnmappedBranches();

    /*
     * Allotments not verified (example: missing rank or score)
     */
    @Query("""
                SELECT COUNT(a)
                FROM StudentAllotment a
                WHERE a.rank IS NULL
            """)
    Integer countUnverifiedAllotments();

}