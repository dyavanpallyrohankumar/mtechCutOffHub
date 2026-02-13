package com.mtech.addmissions.util;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private String error;
    private String path;

    public ErrorResponse(LocalDateTime timestamp, HttpStatus status, String message, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.error = error;
        this.path = path;
    }
}
