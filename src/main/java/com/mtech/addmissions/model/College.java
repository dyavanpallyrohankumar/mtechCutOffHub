package com.mtech.addmissions.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String collegeName;
    @Column(length = 2000)
    private String collegeAddress;
    private String collegeCode;
    // private String district;
    private String collegeType;
    private String universityName;

    @OneToMany
    private List<Branch> branches;

    public College(String collegeName, String collegeAddress, String collegeCode,
            String collegeType, String universityName) {
        this.collegeName = collegeName;
        this.collegeAddress = collegeAddress;
        this.collegeCode = collegeCode;
        this.collegeType = collegeType;
        this.universityName = universityName;
    }

}
