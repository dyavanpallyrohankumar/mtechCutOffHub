package com.mtech.addmissions.service.implementation;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mtech.addmissions.dto.response.BranchCutoffStructuredResponseDTO;
import com.mtech.addmissions.dto.response.BranchCutoffStructuredResponseDTO.CategoryDTO;
import com.mtech.addmissions.dto.response.BranchCutoffStructuredResponseDTO.ExamDTO;
import com.mtech.addmissions.dto.response.BranchCutoffStructuredResponseDTO.GenderCutoffDTO;
import com.mtech.addmissions.dto.response.BranchCutoffStructuredResponseDTO.PhaseDTO;
import com.mtech.addmissions.dto.response.BranchCutoffStructuredResponseDTO.YearDTO;
import com.mtech.addmissions.dto.response.CutoffSummaryDTO;
import com.mtech.addmissions.enums.Exam;
import com.mtech.addmissions.enums.Gender;
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
                // College college = collegeRepository.findByCollegeCode(collegeID)
                // .orElseThrow(() -> new ResourseNotExist("No College Found By College Code"));
                // Branch branch = branchRepository.findByCollegeProgramCode(branchID)
                // .orElseThrow(() -> new ResourseNotExist("No Branch Found By Branch Code"));

                List<CutoffSummary> cutoffs = cutOffRepository.findCutoffsByCollegeCodeAndBranchCode(collegeID,
                                branchID);
                List<CutoffSummaryDTO> dtos = cutoffs.stream()
                                .map((cutoff) -> mapper.map(cutoff, CutoffSummaryDTO.class))
                                .toList();
                return dtos;
        }

        public BranchCutoffStructuredResponseDTO getStructuredCutoffsOfCollegeBranch(
                        String collegeID,
                        String branchID) throws ResourseNotExist {

                College college = collegeRepository.findByCollegeCode(collegeID)
                                .orElseThrow(() -> new ResourseNotExist("No College Found"));

                Branch branch = branchRepository.findByCollegeProgramCode(branchID)
                                .orElseThrow(() -> new ResourseNotExist("No Branch Found"));

                List<CutoffSummary> cutoffs = cutOffRepository.findCutoffsByCollegeCodeAndBranchCode(collegeID,
                                branchID);

                // -----------------------------
                // GROUPING STARTS HERE
                // -----------------------------

                Map<Integer, Map<String, Map<String, Map<Exam, Map<Gender, CutoffSummary>>>>> grouped = cutoffs.stream()
                                .collect(Collectors.groupingBy(
                                                CutoffSummary::getYear,
                                                Collectors.groupingBy(
                                                                CutoffSummary::getPhase,
                                                                Collectors.groupingBy(
                                                                                CutoffSummary::getCategory,
                                                                                Collectors.groupingBy(
                                                                                                CutoffSummary::getExam,
                                                                                                Collectors.toMap(
                                                                                                                CutoffSummary::getGender,
                                                                                                                c -> c))))));

                // -----------------------------
                // CONVERT MAP → DTO
                // -----------------------------

                List<YearDTO> yearList = grouped.entrySet().stream().map(yearEntry -> {

                        Integer year = yearEntry.getKey();

                        List<PhaseDTO> phases = yearEntry.getValue().entrySet().stream().map(phaseEntry -> {

                                String phase = phaseEntry.getKey();

                                List<CategoryDTO> categories = phaseEntry.getValue().entrySet().stream()
                                                .map(catEntry -> {

                                                        String category = catEntry.getKey();

                                                        List<ExamDTO> exams = catEntry.getValue().entrySet().stream()
                                                                        .map(examEntry -> {

                                                                                Exam exam = examEntry.getKey();

                                                                                List<GenderCutoffDTO> genders = examEntry
                                                                                                .getValue().entrySet()
                                                                                                .stream()
                                                                                                .map(genderEntry -> {

                                                                                                        CutoffSummary c = genderEntry
                                                                                                                        .getValue();

                                                                                                        return new GenderCutoffDTO(
                                                                                                                        genderEntry.getKey(),
                                                                                                                        c.getStartRank(),
                                                                                                                        c.getEndRank(),
                                                                                                                        c.getStartPercentile(),
                                                                                                                        c.getEndPercentile());

                                                                                                }).toList();

                                                                                return new ExamDTO(exam, genders);

                                                                        }).toList();

                                                        return new CategoryDTO(category, exams);

                                                }).toList();

                                return new PhaseDTO(phase, categories);

                        }).toList();

                        return new YearDTO(year, phases);

                }).toList();

                return new BranchCutoffStructuredResponseDTO(
                                college.getId(),
                                branch.getId(),
                                yearList);
        }

        // // =========================================================
        // // 2️⃣ AUTO UPDATE CUTOFF WHEN NEW STUDENT ALLOTMENT IS ADDED
        // // =========================================================
        // public void updateCutoffFor(StudentAllotment allotment) {

        // Optional<CutoffSummary> optionalCutoff = cutOffRepository
        // .findByCollegeAndBranchAndCategoryAndGenderAndExamAndPhaseAndYear(
        // allotment.getCollege(),
        // allotment.getBranch(),
        // allotment.getCategory(),
        // allotment.getGender(),
        // allotment.getExam(),
        // allotment.getPhase(),
        // allotment.getYear());

        // Integer newRank = allotment.getRank();
        // Double newPercentile = parsePercentile(allotment.getScoreOrPercentile());

        // // -----------------------------------------------------
        // // CASE 1: FIRST ENTRY → CREATE NEW CUTOFF
        // // -----------------------------------------------------
        // if (optionalCutoff.isEmpty()) {

        // CutoffSummary cutoff = CutoffSummary.builder()
        // .college(allotment.getCollege())
        // .branch(allotment.getBranch())
        // .category(allotment.getCategory())
        // .gender(allotment.getGender())
        // .exam(allotment.getExam())
        // .phase(allotment.getPhase())
        // .year(allotment.getYear())
        // .startRank(newRank)
        // .endRank(newRank)
        // .startPercentile(newPercentile)
        // .endPercentile(newPercentile)
        // .build();

        // cutOffRepository.save(cutoff);
        // return;
        // }

        // // -----------------------------------------------------
        // // CASE 2: UPDATE EXISTING CUTOFF
        // // -----------------------------------------------------
        // CutoffSummary cutoff = optionalCutoff.get();

        // // ---- RANK LOGIC (Lower rank = better) ----
        // if (newRank != null) {
        // if (cutoff.getStartRank() == null || newRank < cutoff.getStartRank()) {
        // cutoff.setStartRank(newRank);
        // }
        // if (cutoff.getEndRank() == null || newRank > cutoff.getEndRank()) {
        // cutoff.setEndRank(newRank);
        // }
        // }

        // // ---- PERCENTILE LOGIC (Higher percentile = better) ----
        // if (newPercentile != null) {
        // if (cutoff.getStartPercentile() == null || newPercentile >
        // cutoff.getStartPercentile()) {
        // cutoff.setStartPercentile(newPercentile);
        // }
        // if (cutoff.getEndPercentile() == null || newPercentile <
        // cutoff.getEndPercentile()) {
        // cutoff.setEndPercentile(newPercentile);
        // }
        // }

        // cutOffRepository.save(cutoff);
        // }

        // =========================================================
        // 2️⃣ SAFE AUTO UPDATE WHEN NEW ALLOTMENT ADDED
        // =========================================================

        @Transactional
        public void updateCutoffFor(StudentAllotment allotment) {

                if (!isValidAllotment(allotment)) {
                        return;
                }

                String normalizedCategory = normalizeCategory(allotment.getCategory());
                String normalizedPhase = normalizePhase(allotment.getPhase());

                Integer newRank = allotment.getRank();
                Double newPercentile = parsePercentile(allotment.getScoreOrPercentile());

                Optional<CutoffSummary> optionalCutoff = cutOffRepository
                                .findByCollegeAndBranchAndCategoryAndGenderAndExamAndPhaseAndYear(
                                                allotment.getCollege(),
                                                allotment.getBranch(),
                                                normalizedCategory,
                                                allotment.getGender(),
                                                allotment.getExam(),
                                                normalizedPhase,
                                                allotment.getYear());

                CutoffSummary cutoff = optionalCutoff.orElseGet(() -> CutoffSummary.builder()
                                .college(allotment.getCollege())
                                .branch(allotment.getBranch())
                                .category(normalizedCategory)
                                .gender(allotment.getGender())
                                .exam(allotment.getExam())
                                .phase(normalizedPhase)
                                .year(allotment.getYear())
                                .build());

                // ---- Rank Logic (Lower = Better) ----
                if (newRank != null) {
                        if (cutoff.getStartRank() == null || newRank < cutoff.getStartRank()) {
                                cutoff.setStartRank(newRank);
                        }
                        if (cutoff.getEndRank() == null || newRank > cutoff.getEndRank()) {
                                cutoff.setEndRank(newRank);
                        }
                }

                // ---- Percentile Logic (Higher = Better) ----
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

        // // =========================================================
        // // 3️⃣ HELPER → CONVERT ENTITY TO DTO
        // // =========================================================
        // private CutoffSummaryDTO toCutoffSummaryDTO(CutoffSummary c) {
        // return CutoffSummaryDTO.builder()
        // .category(c.getCategory())
        // .gender(c.getGender())
        // .exam(c.getExam())
        // .phase(c.getPhase())
        // .year(c.getYear())
        // .startRank(c.getStartRank())
        // .endRank(c.getEndRank())
        // .startPercentile(c.getStartPercentile())
        // .endPercentile(c.getEndPercentile())
        // .build();
        // }

        // =========================================================
        // 4️⃣ HELPER → PARSE PERCENTILE SAFELY
        // =========================================================

        private String normalize(String value) {
                return value == null ? null : value.trim().toUpperCase();
        }

        // @Transactional
        // public void bootstrapAllCutoffs() {

        // cutOffRepository.deleteAll();

        // List<College> colleges = collegeRepository.findAll();

        // for (College college : colleges) {
        // generateCutoffsForCollege(college);
        // }
        // }

        // private void generateCutoffsForCollege(College college) {

        // List<Branch> branches = branchRepository.findByCollege(college);

        // for (Branch branch : branches) {
        // generateCutoffsForBranch(college, branch);
        // }
        // }

        // @Transactional
        // public void generateCutoffsForBranch(College college, Branch branch) {

        // // 1️⃣ Fetch allotments only for this college + branch
        // List<StudentAllotment> allotments =
        // studentAllotmentRepository.findByCollegeAndBranch(college, branch);

        // if (allotments == null || allotments.isEmpty()) {
        // return;
        // }

        // // 2️⃣ Group by logical cutoff key
        // Map<CutoffKey, List<StudentAllotment>> grouped = allotments.stream()
        // .filter(this::isValidAllotment)
        // .collect(Collectors.groupingBy(a -> new CutoffKey(
        // normalizeProgramCode(a.getCollegeProgramCode()),
        // normalizeCategory(a.getCategory()),
        // a.getGender(),
        // a.getExam(),
        // normalizePhase(a.getPhase()),
        // a.getYear())));

        // // 3️⃣ For each group → compute & upsert cutoff
        // for (Map.Entry<CutoffKey, List<StudentAllotment>> entry : grouped.entrySet())
        // {

        // CutoffKey key = entry.getKey();
        // List<StudentAllotment> list = entry.getValue();

        // // ---- Rank computation (lower = better) ----
        // Integer startRank = list.stream()
        // .map(StudentAllotment::getRank)
        // .filter(Objects::nonNull)
        // .min(Integer::compareTo)
        // .orElse(null);

        // Integer endRank = list.stream()
        // .map(StudentAllotment::getRank)
        // .filter(Objects::nonNull)
        // .max(Integer::compareTo)
        // .orElse(null);

        // // ---- Percentile computation (higher = better) ----
        // Double startPercentile = list.stream()
        // .map(a -> parsePercentile(a.getScoreOrPercentile()))
        // .filter(Objects::nonNull)
        // .max(Double::compareTo)
        // .orElse(null);

        // Double endPercentile = list.stream()
        // .map(a -> parsePercentile(a.getScoreOrPercentile()))
        // .filter(Objects::nonNull)
        // .min(Double::compareTo)
        // .orElse(null);

        // // 4️⃣ UPSERT logic (never create duplicates)
        // Optional<CutoffSummary> existing = cutOffRepository
        // .findByCollegeAndBranchAndCategoryAndGenderAndExamAndPhaseAndYear(
        // college,
        // branch,
        // key.getCategory(),
        // key.getGender(),
        // key.getExam(),
        // key.getPhase(),
        // key.getYear());

        // CutoffSummary cutoff;
        // if (existing.isPresent()) {
        // cutoff = existing.get();
        // } else {
        // cutoff = CutoffSummary.builder()
        // .college(college)
        // .branch(branch)
        // .category(key.getCategory())
        // .gender(key.getGender())
        // .exam(key.getExam())
        // .phase(key.getPhase())
        // .year(key.getYear())
        // .build();
        // }

        // cutoff.setStartRank(startRank);
        // cutoff.setEndRank(endRank);
        // cutoff.setStartPercentile(startPercentile);
        // cutoff.setEndPercentile(endPercentile);

        // cutOffRepository.save(cutoff);
        // }
        // }

        // private boolean isValidAllotment(StudentAllotment a) {
        // return a != null
        // && a.getCollegeProgramCode() != null
        // && a.getCategory() != null
        // && a.getGender() != null
        // && a.getExam() != null
        // && a.getPhase() != null
        // && a.getYear() != null;
        // }

        // private String normalizePhase(String phase) {
        // if (phase == null)
        // return null;
        // return phase.trim().toUpperCase();
        // }

        // private String normalizeCategory(String category) {
        // if (category == null)
        // return null;

        // category = category.trim().toUpperCase();

        // if (category.startsWith("SC"))
        // return "SC";
        // if (category.startsWith("ST"))
        // return "ST";

        // return category;
        // }

        // private String normalizeProgramCode(String code) {
        // if (code == null) {
        // throw new IllegalStateException("collegeProgramCode cannot be null");
        // }
        // return code.trim().toUpperCase();
        // }

        // =========================================================
        // 3️⃣ FULL BOOTSTRAP (SAFE)
        // =========================================================

        @Transactional
        public void bootstrapAllCutoffs() {

                cutOffRepository.deleteAllInBatch();

                collegeRepository.findAll()
                                .forEach(this::generateCutoffsForCollege);
        }

        private void generateCutoffsForCollege(College college) {

                branchRepository.findByCollege(college)
                                .forEach(branch -> generateCutoffsForBranch(college, branch));
        }

        @Transactional
        public void generateCutoffsForBranch(College college, Branch branch) {

                List<StudentAllotment> allotments = studentAllotmentRepository.findByCollegeAndBranch(college, branch);

                if (allotments == null || allotments.isEmpty()) {
                        return;
                }

                Map<CutoffKey, List<StudentAllotment>> grouped = allotments.stream()
                                .filter(this::isValidAllotment)
                                .collect(Collectors.groupingBy(a -> new CutoffKey(
                                                normalizeCategory(a.getCategory()),
                                                a.getGender(),
                                                a.getExam(),
                                                normalizePhase(a.getPhase()),
                                                a.getYear())));

                for (Map.Entry<CutoffKey, List<StudentAllotment>> entry : grouped.entrySet()) {

                        CutoffKey key = entry.getKey();
                        List<StudentAllotment> list = entry.getValue();

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

                        Optional<CutoffSummary> existing = cutOffRepository
                                        .findByCollegeAndBranchAndCategoryAndGenderAndExamAndPhaseAndYear(
                                                        college,
                                                        branch,
                                                        key.getCategory(),
                                                        key.getGender(),
                                                        key.getExam(),
                                                        key.getPhase(),
                                                        key.getYear());

                        CutoffSummary cutoff = existing.orElseGet(() -> CutoffSummary.builder()
                                        .college(college)
                                        .branch(branch)
                                        .category(key.getCategory())
                                        .gender(key.getGender())
                                        .exam(key.getExam())
                                        .phase(key.getPhase())
                                        .year(key.getYear())
                                        .build());

                        cutoff.setStartRank(startRank);
                        cutoff.setEndRank(endRank);
                        cutoff.setStartPercentile(startPercentile);
                        cutoff.setEndPercentile(endPercentile);

                        cutOffRepository.save(cutoff);
                }
        }

        // =========================================================
        // 4️⃣ VALIDATION
        // =========================================================

        private boolean isValidAllotment(StudentAllotment a) {
                return a != null
                                && a.getCategory() != null
                                && a.getGender() != null
                                && a.getExam() != null
                                && a.getPhase() != null
                                && a.getYear() != null
                                && a.getRank() != null;
        }

        // =========================================================
        // 5️⃣ NORMALIZATION
        // =========================================================

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

        private String normalizePhase(String phase) {
                return phase == null ? null : phase.trim().toUpperCase();
        }

        // =========================================================
        // 6️⃣ PERCENTILE PARSER
        // =========================================================

        private Double parsePercentile(String value) {

                if (value == null || value.isBlank())
                        return null;

                try {
                        String cleaned = value.contains("(")
                                        ? value.split("\\(")[0]
                                        : value;

                        return Double.parseDouble(cleaned.trim());

                } catch (Exception e) {
                        System.err.println("Invalid percentile format: " + value);
                        return null;
                }
        }

        // =========================================================
        // 7️⃣ DTO MAPPER
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

}
