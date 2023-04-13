package com.example.backdrop.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Setter
@Getter
@Builder
public class ApiResponse {
    private String path;
    private ZonedDateTime zonedDateTime;
    private HttpStatus httpStatus;
    private int statusCode;
    private String message;
}
