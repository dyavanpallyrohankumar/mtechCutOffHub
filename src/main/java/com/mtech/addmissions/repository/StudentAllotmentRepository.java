package com.mtech.addmissions.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mtech.addmissions.model.Branch;
import com.mtech.addmissions.model.College;
import com.mtech.addmissions.model.StudentAllotment;

public interface StudentAllotmentRepository extends JpaRepository<StudentAllotment, Long> {
    List<StudentAllotment> findByCollegeAndBranch(College college, Branch branch);

    List<StudentAllotment> findByCollege(College college);

    List<StudentAllotment> findByBranch(Branch branch);

}
