package com.mtech.addmissions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.mtech.addmissions.model.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByUsername(String username);

    Optional<AdminUser> findByEmail(String email);
}
