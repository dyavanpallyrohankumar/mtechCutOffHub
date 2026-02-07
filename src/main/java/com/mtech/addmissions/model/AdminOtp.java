package com.mtech.addmissions.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "admin_otp")
@Data
public class AdminOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private AdminUser admin;

    private String otp;

    private LocalDateTime expiresAt;

    private boolean verified;
}
