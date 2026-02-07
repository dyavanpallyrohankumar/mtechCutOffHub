package com.mtech.addmissions.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtech.addmissions.dto.response.StudentAllotmentDTO;
import com.mtech.addmissions.exception.ResourseNotExist;
import com.mtech.addmissions.service.implementation.AllotmentServiceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/admin/allotments")
@PreAuthorize("hasRole('ADMIN')")
public class AdminStudentController {

    @Autowired
    AllotmentServiceImp allotmentServiceImp;

    @PostMapping()
    public ResponseEntity<?> alloteStudent(@RequestBody StudentAllotmentDTO student) throws ResourseNotExist {
        return new ResponseEntity(allotmentServiceImp.alloteStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("/college/{collegeID}")
    public ResponseEntity<?> collegeallotedStudents(@PathVariable String collegeID) throws ResourseNotExist {
        return new ResponseEntity(allotmentServiceImp.collegeAllotedStudents(collegeID), HttpStatus.OK);
    }

    @GetMapping("/branch/{collegeProgramCode}")
    public ResponseEntity<?> branchallotedStudents(@PathVariable String collegeProgramCode) throws ResourseNotExist {
        return new ResponseEntity(allotmentServiceImp.branchAllotedStudents(collegeProgramCode), HttpStatus.OK);
    }

    @GetMapping("/college/{collegeID}/branch/{collegeProgramCode}")
    public ResponseEntity<?> allotedStudents(@PathVariable String collegeID, @PathVariable String collegeProgramCode)
            throws ResourseNotExist {
        return new ResponseEntity(allotmentServiceImp.allotmentsOfCollegeAndBranch(collegeID, collegeProgramCode),
                HttpStatus.OK);
    }

}
