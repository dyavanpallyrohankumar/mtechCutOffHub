package com.mtech.addmissions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mtech.addmissions.dto.response.CollegeListDTO;
import com.mtech.addmissions.dto.response.PageResponse;
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

    @GetMapping
    public ResponseEntity<PageResponse<CollegeListDTO>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Page<CollegeListDTO> pageData = collegeServiceImp.getAllColleges(page, size);

        return ResponseEntity.ok(new PageResponse<>(pageData));
    }

    // @GetMapping()
    // public ResponseEntity<List<CollegeDTO>> getAll() {
    // List<CollegeDTO> response = collegeServiceImp.getAllColleges();

    // return ResponseEntity.ok(response);
    // }

    @GetMapping("/{CollegeID}")
    public ResponseEntity<?> getOne(@PathVariable String CollegeID) throws ResourseNotExist {
        return new ResponseEntity<>(collegeServiceImp.getCollegeByID(CollegeID), HttpStatus.OK);
    }

    @GetMapping("/{CollegeID}/branches")
    public ResponseEntity<?> getAllbranches(@PathVariable String CollegeID) throws ResourseNotExist {
        return new ResponseEntity<>(branchServiceImp.getBranchesbyCollegeID(CollegeID), HttpStatus.OK);
    }

}
