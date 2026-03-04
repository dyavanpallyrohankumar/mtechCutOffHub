package com.mtech.addmissions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mtech.addmissions.model.AdminActivity;

import java.util.List;

public interface AdminActivityRepository extends JpaRepository<AdminActivity, Long> {

    List<AdminActivity> findTop10ByOrderByCreatedAtDesc();

}