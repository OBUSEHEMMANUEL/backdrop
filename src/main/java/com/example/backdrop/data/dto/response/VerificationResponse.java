package com.example.backdrop.data.dto.response;

import lombok.Data;

@Data
public class VerificationResponse {
    private String status;
    private String message;
    private RecipientAccountData data;

}
