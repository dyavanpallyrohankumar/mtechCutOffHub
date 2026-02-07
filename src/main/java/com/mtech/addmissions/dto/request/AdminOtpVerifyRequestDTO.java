package com.mtech.addmissions.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminOtpVerifyRequestDTO {
    private String username;
    private String otp;
}
