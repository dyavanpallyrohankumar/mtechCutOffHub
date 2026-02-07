package com.mtech.addmissions.service.implementation;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mtech.addmissions.dto.response.StudentAllotmentDTO;
import com.mtech.addmissions.exception.ResourseNotExist;
import com.mtech.addmissions.model.*;
import com.mtech.addmissions.repository.*;

@Service
public class AllotmentServiceImp {
        @Autowired
        StudentAllotmentRepository studentAllotmentRepository;

        @Autowired
        CollegeRepository collegeRepository;

        @Autowired
        BranchRepository branchRepository;

        @Autowired
        CutoffServiceImp cutoffServiceImp;

        public ModelMapper mapper = new ModelMapper();

        public StudentAllotmentDTO alloteStudent(StudentAllotmentDTO student) throws ResourseNotExist {
                College college = collegeRepository.findByCollegeCode(student.getCollegeProgramCode())
                                .orElseThrow(() -> new ResourseNotExist("No College Found By College Code"));

                Branch branch = branchRepository.findById(student.getBranchId())
                                .orElseThrow(() -> new ResourseNotExist("No Branch Found By Branch Code"));

                StudentAllotment allotment = new StudentAllotment(college, branch, student.getStudentName(),
                                student.getCategory(), student.getAllotedCategory(), student.getPhase(),
                                student.getScoreOrPercentile(), student.getRank(), student.getYear());
                allotment.setGender(student.getGender());
                allotment.setExam(student.getExam());
                allotment.setRegion(student.getRegion());
                cutoffServiceImp.updateCutoffFor(allotment); // automatic

                return mapper.map(allotment, StudentAllotmentDTO.class);
        }

        public List<StudentAllotmentDTO> collegeAllotedStudents(String collegeID) throws ResourseNotExist {
                College college = collegeRepository.findByCollegeCode(collegeID)
                                .orElseThrow(() -> new ResourseNotExist("No College Found By College Code"));
                List<StudentAllotmentDTO> dtos = studentAllotmentRepository.findByCollege(college)
                                .stream()
                                .map((student) -> mapper.map(student, StudentAllotmentDTO.class))
                                .toList();
                return dtos;
        }

        public List<StudentAllotmentDTO> branchAllotedStudents(String collegeProgramCode) throws ResourseNotExist {

                Branch branch = branchRepository.findByCollegeProgramCode(collegeProgramCode)
                                .orElseThrow(() -> new ResourseNotExist("No Branch Found By Branch Code"));

                List<StudentAllotmentDTO> dtos = studentAllotmentRepository.findByBranch(branch)
                                .stream()
                                .map((student) -> mapper.map(student, StudentAllotmentDTO.class))
                                .toList();
                return dtos;
        }

        public List<StudentAllotmentDTO> allotmentsOfCollegeAndBranch(String collegeID, String collegeProgramCode)
                        throws ResourseNotExist {
                College college = collegeRepository.findByCollegeCode(collegeID)
                                .orElseThrow(() -> new ResourseNotExist("No College Found By College Code"));
                Branch branch = branchRepository.findByCollegeProgramCode(collegeProgramCode)
                                .orElseThrow(() -> new ResourseNotExist("No Branch Found By Branch Code"));
                List<StudentAllotmentDTO> dtos = studentAllotmentRepository.findByCollegeAndBranch(college, branch)
                                .stream()
                                .map((student) -> mapper.map(student, StudentAllotmentDTO.class)).toList();
                return dtos;
        }
}
