package com.mtech.addmissions.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/admin/colleges")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCollegeController {
    @Autowired
    CollegeServiceImp collegeServiceImp;

    @Autowired
    BranchServiceImp branchServiceImp;

    @PostMapping()
    public ResponseEntity<CollegeDTO> create(@RequestBody CollegeDTO college) throws ResourseAlreadyExist {
        return new ResponseEntity(collegeServiceImp.addCollege(college), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<CollegeDTO> update(@RequestBody CollegeDTO college) throws ResourseNotExist {
        return new ResponseEntity(collegeServiceImp.update(college), HttpStatus.OK);
    }

    @DeleteMapping("/{collegeCode}")
    public ResponseEntity<CollegeDTO> delete(@PathVariable String collegeCode) throws ResourseNotExist {
        return new ResponseEntity(collegeServiceImp.deleteCollege(collegeCode), HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{CollegeID}/branches")
    public ResponseEntity<?> addbranches(@PathVariable String CollegeID, @RequestBody List<BranchDTO> branches)
            throws ResourseNotExist {
        return new ResponseEntity(branchServiceImp.addBranchtoCollege(CollegeID, branches), HttpStatus.CREATED);
    }
}
