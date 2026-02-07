package com.mtech.addmissions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.mtech.addmissions.model.AdminUser;
import com.mtech.addmissions.model.AdminOtp;

public interface AdminOtpRepository extends JpaRepository<AdminOtp, Long> {
    Optional<AdminOtp> findByAdmin(AdminUser admin);
}
