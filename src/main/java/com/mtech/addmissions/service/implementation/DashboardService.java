package com.mtech.addmissions.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.mtech.addmissions.dto.response.DashboardResponseDTO.ActivityDto;
import com.mtech.addmissions.dto.response.DashboardResponseDTO.DashboardSummaryDto;
import com.mtech.addmissions.dto.response.DashboardResponseDTO.DataHealthDto;
import com.mtech.addmissions.dto.response.DashboardResponseDTO.YearlyTrendDto;
import com.mtech.addmissions.repository.AdminActivityRepository;
import com.mtech.addmissions.repository.BranchRepository;
import com.mtech.addmissions.repository.CollegeRepository;
import com.mtech.addmissions.repository.StudentAllotmentRepository;

@Service
public class DashboardService {

    private final CollegeRepository collegeRepository;
    private final BranchRepository branchRepository;
    private final StudentAllotmentRepository allotmentRepository;
    private final AdminActivityRepository adminActivityRepository;

    public DashboardService(
            CollegeRepository collegeRepository,
            BranchRepository branchRepository,
            StudentAllotmentRepository allotmentRepository,
            AdminActivityRepository adminActivityRepository) {

        this.collegeRepository = collegeRepository;
        this.branchRepository = branchRepository;
        this.allotmentRepository = allotmentRepository;
        this.adminActivityRepository = adminActivityRepository;
    }

    public DashboardSummaryDto getSummary() {

        DashboardSummaryDto dto = new DashboardSummaryDto();

        dto.setTotalColleges((int) collegeRepository.count());
        dto.setTotalBranches((int) branchRepository.count());
        dto.setTotalAllotments((int) allotmentRepository.count());
        dto.setActiveYears(allotmentRepository.countDistinctYears());

        dto.setLastImportDate(LocalDateTime.now().toString()); // replace with actual import log
        dto.setSystemVersion("1.0.0");

        return dto;
    }

    public List<YearlyTrendDto> getYearlyAllotments() {

        return allotmentRepository.getYearlyAllotments()
                .stream()
                .map(obj -> {
                    YearlyTrendDto dto = new YearlyTrendDto();
                    dto.setYear((Integer) obj[0]);
                    dto.setCount(((Long) obj[1]).intValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ActivityDto> getRecentActivity() {

        return adminActivityRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(activity -> {

                    ActivityDto dto = new ActivityDto();

                    dto.setId(activity.getId());
                    dto.setType(activity.getType());
                    dto.setMessage(activity.getMessage());
                    dto.setCreatedAt(activity.getCreatedAt().toString());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public DataHealthDto getDataHealth() {

        DataHealthDto dto = new DataHealthDto();

        dto.setMissingCategories(allotmentRepository.countMissingCategories());
        dto.setDuplicateRecords(allotmentRepository.countDuplicateRecords());
        dto.setUnmappedBranches(allotmentRepository.countUnmappedBranches());
        dto.setUnverifiedAllotments(allotmentRepository.countUnverifiedAllotments());

        return dto;
    }

}
