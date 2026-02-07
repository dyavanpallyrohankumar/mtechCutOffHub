package com.mtech.addmissions.model;

import com.mtech.addmissions.enums.Role;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "admin_user")
@Data
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ADMIN;
}
