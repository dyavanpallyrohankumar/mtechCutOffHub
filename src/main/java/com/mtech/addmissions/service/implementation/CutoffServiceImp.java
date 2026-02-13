package com.mtech.addmissions.service.implementation;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mtech.addmissions.dto.response.CutoffSummaryDTO;
import com.mtech.addmissions.exception.ResourseNotExist;
import com.mtech.addmissions.model.*;
import com.mtech.addmissions.repository.*;
import jakarta.transaction.Transactional;

@Service
public class CutoffServiceImp {
        @Autowired
        CutoffRepository cutOffRepository;

        @Autowired
        StudentAllotmentRepository studentAllotmentRepository;

        @Autowired
        CollegeRepository collegeRepository;

        @Autowired
        BranchRepository branchRepository;

        public ModelMapper mapper = new ModelMapper();

        public List<CutoffSummaryDTO> getAllcutOffsOfCollege(String collegeID) throws ResourseNotExist {
                College college = collegeRepository.findByCollegeCode(collegeID)
                                .orElseThrow(() -> new ResourseNotExist("No College Found By College Code"));
                List<CutoffSummaryDTO> dtos = cutOffRepository.findAllByCollege(college).stream()
                                .map((cutoffs) -> mapper.map(cutoffs, CutoffSummaryDTO.class)).toList();
                return dtos;
        }

        public List<CutoffSummaryDTO> getAllcutOffsOfCollegeBranch(String collegeID, String branchID)
                        throws ResourseNotExist {
                College college = collegeRepository.findByCollegeCode(collegeID)
                                .orElseThrow(() -> new ResourseNotExist("No College Found By College Code"));
                Branch branch = branchRepository.findByCollegeProgramCode(branchID)
                                .orElseThrow(() -> new ResourseNotExist("No Branch Found By Branch Code"));

                List<CutoffSummary> cutoffs = cutOffRepository.findCutoffsByCollegeCodeAndBranchCode(collegeID,
                                branchID);
                List<CutoffSummaryDTO> dtos = cutoffs.stream()
                                .map((cutoff) -> mapper.map(cutoff, CutoffSummaryDTO.class))
                                .toList();
                return dtos;
        }

        // =========================================================
        // 2️⃣ AUTO UPDATE CUTOFF WHEN NEW STUDENT ALLOTMENT IS ADDED
        // =========================================================
        public void updateCutoffFor(StudentAllotment allotment) {

                Optional<CutoffSummary> optionalCutoff = cutOffRepository
                                .findByCollegeAndBranchAndCategoryAndGenderAndExamAndPhaseAndYear(
                                                allotment.getCollege(),
                                                allotment.getBranch(),
                                                allotment.getCategory(),
                                                allotment.getGender(),
                                                allotment.getExam(),
                                                allotment.getPhase(),
                                                allotment.getYear());

                Integer newRank = allotment.getRank();
                Double newPercentile = parsePercentile(allotment.getScoreOrPercentile());

                // -----------------------------------------------------
                // CASE 1: FIRST ENTRY → CREATE NEW CUTOFF
                // -----------------------------------------------------
                if (optionalCutoff.isEmpty()) {

                        CutoffSummary cutoff = CutoffSummary.builder()
                                        .college(allotment.getCollege())
                                        .branch(allotment.getBranch())
                                        .category(allotment.getCategory())
                                        .gender(allotment.getGender())
                                        .exam(allotment.getExam())
                                        .phase(allotment.getPhase())
                                        .year(allotment.getYear())
                                        .startRank(newRank)
                                        .endRank(newRank)
                                        .startPercentile(newPercentile)
                                        .endPercentile(newPercentile)
                                        .build();

                        cutOffRepository.save(cutoff);
                        return;
                }

                // -----------------------------------------------------
                // CASE 2: UPDATE EXISTING CUTOFF
                // -----------------------------------------------------
                CutoffSummary cutoff = optionalCutoff.get();

                // ---- RANK LOGIC (Lower rank = better) ----
                if (newRank != null) {
                        if (cutoff.getStartRank() == null || newRank < cutoff.getStartRank()) {
                                cutoff.setStartRank(newRank);
                        }
                        if (cutoff.getEndRank() == null || newRank > cutoff.getEndRank()) {
                                cutoff.setEndRank(newRank);
                        }
                }

                // ---- PERCENTILE LOGIC (Higher percentile = better) ----
                if (newPercentile != null) {
                        if (cutoff.getStartPercentile() == null || newPercentile > cutoff.getStartPercentile()) {
                                cutoff.setStartPercentile(newPercentile);
                        }
                        if (cutoff.getEndPercentile() == null || newPercentile < cutoff.getEndPercentile()) {
                                cutoff.setEndPercentile(newPercentile);
                        }
                }

                cutOffRepository.save(cutoff);
        }

        // =========================================================
        // 3️⃣ HELPER → CONVERT ENTITY TO DTO
        // =========================================================
        private CutoffSummaryDTO toCutoffSummaryDTO(CutoffSummary c) {
                return CutoffSummaryDTO.builder()
                                .category(c.getCategory())
                                .gender(c.getGender())
                                .exam(c.getExam())
                                .phase(c.getPhase())
                                .year(c.getYear())
                                .startRank(c.getStartRank())
                                .endRank(c.getEndRank())
                                .startPercentile(c.getStartPercentile())
                                .endPercentile(c.getEndPercentile())
                                .build();
        }

        // =========================================================
        // 4️⃣ HELPER → PARSE PERCENTILE SAFELY
        // =========================================================
        private Double parsePercentile(String value) {
                if (value == null || value.isBlank())
                        return null;

                try {
                        // Handles formats like "85.774 (998)" or "563"
                        if (value.contains("(")) {
                                return Double.parseDouble(value.split("\\(")[0].trim());
                        }
                        return Double.parseDouble(value.trim());
                } catch (Exception e) {
                        return null;
                }
        }

        private String normalize(String value) {
                return value == null ? null : value.trim().toUpperCase();
        }

        @Transactional
        public void bootstrapAllCutoffs() {

                cutOffRepository.deleteAll();

                List<College> colleges = collegeRepository.findAll();

                for (College college : colleges) {
                        generateCutoffsForCollege(college);
                }
        }

        private void generateCutoffsForCollege(College college) {

                List<Branch> branches = branchRepository.findByCollege(college);

                for (Branch branch : branches) {
                        generateCutoffsForBranch(college, branch);
                }
        }

        @Transactional
        public void generateCutoffsForBranch(College college, Branch branch) {

                // 1️⃣ Fetch allotments only for this college + branch
                List<StudentAllotment> allotments = studentAllotmentRepository.findByCollegeAndBranch(college, branch);

                if (allotments == null || allotments.isEmpty()) {
                        return;
                }

                // 2️⃣ Group by logical cutoff key
                Map<CutoffKey, List<StudentAllotment>> grouped = allotments.stream()
                                .filter(this::isValidAllotment)
                                .collect(Collectors.groupingBy(a -> new CutoffKey(
                                                normalizeProgramCode(a.getCollegeProgramCode()),
                                                normalizeCategory(a.getCategory()),
                                                a.getGender(),
                                                a.getExam(),
                                                normalizePhase(a.getPhase()),
                                                a.getYear())));

                // 3️⃣ For each group → compute & upsert cutoff
                for (Map.Entry<CutoffKey, List<StudentAllotment>> entry : grouped.entrySet()) {

                        CutoffKey key = entry.getKey();
                        List<StudentAllotment> list = entry.getValue();

                        // ---- Rank computation (lower = better) ----
                        Integer startRank = list.stream()
                                        .map(StudentAllotment::getRank)
                                        .filter(Objects::nonNull)
                                        .min(Integer::compareTo)
                                        .orElse(null);

                        Integer endRank = list.stream()
                                        .map(StudentAllotment::getRank)
                                        .filter(Objects::nonNull)
                                        .max(Integer::compareTo)
                                        .orElse(null);

                        // ---- Percentile computation (higher = better) ----
                        Double startPercentile = list.stream()
                                        .map(a -> parsePercentile(a.getScoreOrPercentile()))
                                        .filter(Objects::nonNull)
                                        .max(Double::compareTo)
                                        .orElse(null);

                        Double endPercentile = list.stream()
                                        .map(a -> parsePercentile(a.getScoreOrPercentile()))
                                        .filter(Objects::nonNull)
                                        .min(Double::compareTo)
                                        .orElse(null);

                        // 4️⃣ UPSERT logic (never create duplicates)
                        Optional<CutoffSummary> existing = cutOffRepository
                                        .findByCollegeAndBranchAndCategoryAndGenderAndExamAndPhaseAndYear(
                                                        college,
                                                        branch,
                                                        key.getCategory(),
                                                        key.getGender(),
                                                        key.getExam(),
                                                        key.getPhase(),
                                                        key.getYear());

                        CutoffSummary cutoff;
                        if (existing.isPresent()) {
                                cutoff = existing.get();
                        } else {
                                cutoff = CutoffSummary.builder()
                                                .college(college)
                                                .branch(branch)
                                                .category(key.getCategory())
                                                .gender(key.getGender())
                                                .exam(key.getExam())
                                                .phase(key.getPhase())
                                                .year(key.getYear())
                                                .build();
                        }

                        cutoff.setStartRank(startRank);
                        cutoff.setEndRank(endRank);
                        cutoff.setStartPercentile(startPercentile);
                        cutoff.setEndPercentile(endPercentile);

                        cutOffRepository.save(cutoff);
                }
        }

        private boolean isValidAllotment(StudentAllotment a) {
                return a != null
                                && a.getCollegeProgramCode() != null
                                && a.getCategory() != null
                                && a.getGender() != null
                                && a.getExam() != null
                                && a.getPhase() != null
                                && a.getYear() != null;
        }

        private String normalizePhase(String phase) {
                if (phase == null)
                        return null;
                return phase.trim().toUpperCase();
        }

        private String normalizeCategory(String category) {
                if (category == null)
                        return null;

                category = category.trim().toUpperCase();

                if (category.startsWith("SC"))
                        return "SC";
                if (category.startsWith("ST"))
                        return "ST";

                return category;
        }

        private String normalizeProgramCode(String code) {
                if (code == null) {
                        throw new IllegalStateException("collegeProgramCode cannot be null");
                }
                return code.trim().toUpperCase();
        }

}
