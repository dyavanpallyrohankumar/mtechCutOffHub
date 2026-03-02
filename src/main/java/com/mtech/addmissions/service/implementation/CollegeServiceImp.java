package com.mtech.addmissions.service.implementation;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtech.addmissions.dto.request.CollegeDTO;
import com.mtech.addmissions.dto.response.CollegeListDTO;
import com.mtech.addmissions.exception.ResourseAlreadyExist;
import com.mtech.addmissions.exception.ResourseNotExist;
import com.mtech.addmissions.model.College;
import com.mtech.addmissions.repository.BranchRepository;
import com.mtech.addmissions.repository.CollegeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class CollegeServiceImp {
    @Autowired
    CollegeRepository collegeRepository;

    @Autowired
    BranchRepository branchRepository;

    public ModelMapper mapper = new ModelMapper();

    public CollegeDTO addCollege(CollegeDTO college) throws ResourseAlreadyExist {

        Optional<College> existing = collegeRepository.findByCollegeCode(college.getCollegeCode());
        if (existing.isPresent())
            throw new ResourseAlreadyExist("Already College Exist with Name: " + existing.get().getCollegeCode());

        College newCollege = new College(college.getCollegeName(), college.getCollegeAddress(),
                college.getCollegeCode(), college.getCollegeType(), college.getUniversityName());

        return mapper.map(collegeRepository.save(newCollege), CollegeDTO.class);
    }

    public CollegeListDTO update(CollegeDTO college) throws ResourseNotExist {
        Optional<College> existing = collegeRepository.findByCollegeCode(college.getCollegeCode());
        if (existing.isPresent()) {
            existing.get().setCollegeAddress(college.getCollegeAddress());
            existing.get().setCollegeName(college.getCollegeName());
            existing.get().setCollegeType(college.getCollegeType());
            existing.get().setUniversityName(college.getUniversityName());
            return mapper.map(collegeRepository.save(existing.get()), CollegeListDTO.class);
        }
        throw new ResourseNotExist(" College Not Exist with Code: " + college.getCollegeCode());
    }

    public String deleteCollege(String collegeCode) throws ResourseNotExist {
        College college = collegeRepository.findByCollegeCode(collegeCode)
                .orElseThrow(() -> new ResourseNotExist("No College Found"));
        collegeRepository.delete(college);
        return "College Deleted Sucessfully";
    }

    // for normal user
    public Page<CollegeListDTO> getAllColleges(Integer page, Integer size) {

        int pageNumber = (page != null && page >= 0) ? page : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<College> collegePage = collegeRepository.findAll(pageable);
        System.out.println(collegePage);
        // return null;
        return collegePage.map(college -> mapper.map(college, CollegeListDTO.class));
    }

    // for Admin
    public List<CollegeListDTO> getAllColleges() {
        return collegeRepository.findAll().stream().map((college) -> mapper.map(college, CollegeListDTO.class))
                .toList();
    }

    public CollegeDTO getCollegeByID(String collegeID) throws ResourseNotExist {
        College college = collegeRepository.findByCollegeCode(collegeID)
                .orElseThrow(() -> new ResourseNotExist("No College Found By College Code"));
        return mapper.map(college, CollegeDTO.class);
    }

}
