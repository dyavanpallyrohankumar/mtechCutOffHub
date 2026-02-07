package com.mtech.addmissions.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mtech.addmissions.model.College;
import java.util.List;

public interface CollegeRepository extends JpaRepository<College, Long> {
    public Optional<College> findByCollegeCode(String collegeCode);
}