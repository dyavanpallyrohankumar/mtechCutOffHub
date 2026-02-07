package com.mtech.addmissions.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mtech.addmissions.model.Branch;
import com.mtech.addmissions.model.College;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    public List<Branch> findAllByCollege(College college);

    public Optional<Branch> findByCollegeProgramCode(String collegeProgramCode);

    public List<Branch> findByCollege(College college);

}
