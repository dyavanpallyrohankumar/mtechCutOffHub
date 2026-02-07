package com.mtech.addmissions.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginRequestDTO {
    private String username;
    private String password;
    private String email;
}
