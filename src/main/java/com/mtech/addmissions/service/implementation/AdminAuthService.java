package com.mtech.addmissions.service.implementation;

import com.mtech.addmissions.dto.request.AdminLoginRequestDTO;
import com.mtech.addmissions.dto.request.AdminOtpVerifyRequestDTO;
import com.mtech.addmissions.dto.response.JwtResponseDTO;
import com.mtech.addmissions.model.AdminOtp;
import com.mtech.addmissions.model.AdminUser;
import com.mtech.addmissions.repository.AdminOtpRepository;
import com.mtech.addmissions.repository.AdminUserRepository;
import com.mtech.addmissions.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mtech.addmissions.exception.Credentials;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import com.mtech.addmissions.enums.Role;

@Service
public class AdminAuthService {

    @Autowired
    public AdminUserRepository adminRepo;

    @Autowired
    public AdminOtpRepository otpRepo;

    @Autowired
    public EmailService emailService;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // STEP 1: username + password
    public String login(AdminLoginRequestDTO dto) throws Credentials {

        AdminUser admin = adminRepo.findByUsername(dto.getUsername())
                .orElseThrow(() -> new Credentials("Invalid credentials"));

        if (!passwordEncoder.matches(dto.getPassword(), admin.getPasswordHash())) {
            throw new Credentials("Invalid credentials");
        }

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        AdminOtp adminOtp = otpRepo.findByAdmin(admin).orElse(new AdminOtp());
        adminOtp.setAdmin(admin);
        adminOtp.setOtp(otp);
        adminOtp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        adminOtp.setVerified(false);

        otpRepo.save(adminOtp);

        emailService.sendOtp(admin.getEmail(), otp);
        return "OTP sent to registered email";
    }

    public String register(AdminLoginRequestDTO dto) throws Credentials {

        // Only ONE admin allowed
        if (adminRepo.count() > 0) {
            throw new Credentials("Admin already registered");
        }

        // Check username
        if (adminRepo.findByUsername(dto.getUsername()).isPresent()) {
            throw new Credentials("Username already exists");
        }

        // Check email
        if (adminRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new Credentials("Email already exists");
        }

        AdminUser admin = new AdminUser();
        admin.setUsername(dto.getUsername());
        admin.setEmail(dto.getEmail());

        // 🔐 HASH PASSWORD
        admin.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        admin.setRole(Role.ADMIN);

        adminRepo.save(admin);
        return "Admin registered successfully";
    }

    // STEP 2: OTP verification
    public JwtResponseDTO verifyOtp(AdminOtpVerifyRequestDTO dto) throws Credentials {

        AdminUser admin = adminRepo.findByUsername(dto.getUsername())
                .orElseThrow(() -> new Credentials("Invalid user"));

        AdminOtp otp = otpRepo.findByAdmin(admin)
                .orElseThrow(() -> new Credentials("OTP not found"));

        if (otp.isVerified())
            throw new Credentials("OTP already used");

        if (otp.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new Credentials("OTP expired");

        if (!otp.getOtp().equals(dto.getOtp()))
            throw new Credentials("Invalid OTP");

        otp.setVerified(true);
        otpRepo.save(otp);

        String token = jwtUtil.generateToken(admin.getUsername(), admin.getRole().toString());
        return new JwtResponseDTO(token, 43200000);
    }
}
