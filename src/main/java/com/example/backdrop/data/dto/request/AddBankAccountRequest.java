package com.example.backdrop.data.dto.request;

import lombok.Getter;
import lombok.Setter;


public record AddBankAccountRequest(String accountNumber, String bankCode, String accountName) {
}
