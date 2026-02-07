package com.mtech.addmissions.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
    private long expiresIn;
}
