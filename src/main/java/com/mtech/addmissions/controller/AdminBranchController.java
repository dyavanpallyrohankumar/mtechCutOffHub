package com.mtech.addmissions.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtech.addmissions.dto.request.BranchDTO;
import com.mtech.addmissions.dto.response.CollegeListDTO;
import com.mtech.addmissions.exception.ResourseNotExist;
import com.mtech.addmissions.service.implementation.BranchServiceImp;
import com.mtech.addmissions.service.implementation.CollegeServiceImp;

@RestController
@RequestMapping("/api/admin/branches")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBranchController {

    @Autowired
    CollegeServiceImp collegeServiceImp;

    @Autowired
    BranchServiceImp branchServiceImp;

    @GetMapping()
    public ResponseEntity<List<BranchDTO>> getAll() throws ResourseNotExist {
        return ResponseEntity.ok(branchServiceImp.getBranches());
    }
}