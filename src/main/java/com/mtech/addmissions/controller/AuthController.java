package com.mtech.addmissions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mtech.addmissions.dto.request.AdminLoginRequestDTO;
import com.mtech.addmissions.dto.request.AdminOtpVerifyRequestDTO;
import com.mtech.addmissions.dto.response.JwtResponseDTO;
import com.mtech.addmissions.exception.Credentials;
import com.mtech.addmissions.service.implementation.AdminAuthService;

@RestController
@RequestMapping("/api/admin/auth")
public class AuthController {
    @Autowired
    AdminAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequestDTO dto) throws Credentials {
        return new ResponseEntity<>(authService.login(dto), HttpStatus.OK);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<JwtResponseDTO> verifyOtp(@RequestBody AdminOtpVerifyRequestDTO dto) throws Credentials {
        return ResponseEntity.ok(authService.verifyOtp(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AdminLoginRequestDTO dto) throws Credentials {
        return new ResponseEntity<>(authService.register(dto), HttpStatus.CREATED);
    }
}
