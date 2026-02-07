package com.mtech.addmissions.service.implementation;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mtech.addmissions.dto.request.BranchDTO;
import com.mtech.addmissions.dto.response.CollegeWithBranchesDTO;
import com.mtech.addmissions.exception.ResourseNotExist;
import com.mtech.addmissions.model.*;
import com.mtech.addmissions.repository.BranchRepository;
import com.mtech.addmissions.repository.CollegeRepository;

@Service
public class BranchServiceImp {

        @Autowired
        CollegeRepository collegeRepository;

        @Autowired
        BranchRepository branchRepository;

        public ModelMapper mapper = new ModelMapper();

        public CollegeWithBranchesDTO addBranchtoCollege(String collegeID, List<BranchDTO> branchesdto)
                        throws ResourseNotExist {
                College college = collegeRepository.findByCollegeCode(collegeID)
                                .orElseThrow(() -> new ResourseNotExist("NO college Found"));
                List<Branch> branches = branchesdto.stream()
                                .map((branch) -> new Branch(branch.getBranchName(), branch.getCollegeProgramCode()))
                                .toList();

                college.setBranches(branches);
                return mapper.map(college, CollegeWithBranchesDTO.class);
        }

        public List<BranchDTO> getBranchesbyCollegeID(String collegeID) throws ResourseNotExist {
                College college = collegeRepository.findByCollegeCode(collegeID)
                                .orElseThrow(() -> new ResourseNotExist("NO college Found"));
                // System.out.println(branchRepository.findAllByCollege(college));
                // branchRepository.findAllByCollege(college);
                List<BranchDTO> dtos = branchRepository.findAllByCollege(college).stream()
                                .map((branch) -> mapper.map(branch, BranchDTO.class))
                                .toList();
                return dtos;
        }

}
