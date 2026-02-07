package com.mtech.addmissions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtech.addmissions.exception.ResourseNotExist;
import com.mtech.addmissions.service.implementation.CutoffServiceImp;

@RestController
@RequestMapping("/api/public/cutoffs")
public class PublicCutOffController {

    @Autowired
    CutoffServiceImp cutOffServiceimp;

    @GetMapping("/college/{collegeID}")
    public ResponseEntity<?> collegeCutoff(@PathVariable String collegeID) throws ResourseNotExist {
        return new ResponseEntity(cutOffServiceimp.getAllcutOffsOfCollege(collegeID), HttpStatus.OK);
    }

    @GetMapping("/college/{collegeID}/branch/{branchID}")
    public ResponseEntity<?> collegeCutoff(@PathVariable String collegeID, @PathVariable String branchID)
            throws ResourseNotExist {
        return new ResponseEntity(cutOffServiceimp.getAllcutOffsOfCollegeBranch(collegeID, branchID),
                HttpStatus.OK);
    }
}
