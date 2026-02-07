package com.mtech.addmissions.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtech.addmissions.dto.request.BranchDTO;
import com.mtech.addmissions.dto.request.CollegeDTO;
import com.mtech.addmissions.exception.ResourseAlreadyExist;
import com.mtech.addmissions.exception.ResourseNotExist;
import com.mtech.addmissions.service.implementation.BranchServiceImp;
import com.mtech.addmissions.service.implementation.CollegeServiceImp;

@RestController
@RequestMapping("/api/public/colleges")
public class PublicCollegeController {
    @Autowired
    CollegeServiceImp collegeServiceImp;

    @Autowired
    BranchServiceImp branchServiceImp;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return new ResponseEntity(collegeServiceImp.getAllColleges(), HttpStatus.OK);
    }

    @GetMapping("/{CollegeID}")
    public ResponseEntity<?> getOne(@PathVariable String CollegeID) throws ResourseNotExist {
        return new ResponseEntity(collegeServiceImp.getCollegeByID(CollegeID), HttpStatus.OK);
    }

    @GetMapping("/{CollegeID}/branches")
    public ResponseEntity<?> getAllbranches(@PathVariable String CollegeID) throws ResourseNotExist {
        return new ResponseEntity(branchServiceImp.getBranchesbyCollegeID(CollegeID), HttpStatus.OK);
    }

}
