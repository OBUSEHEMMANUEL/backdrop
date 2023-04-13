package com.example.backdrop.service;

import com.example.backdrop.data.dto.request.AddBankAccountRequest;
import com.example.backdrop.data.dto.request.AccountNameRequest;
import com.example.backdrop.data.dto.request.RegistrationRequest;
import com.example.backdrop.data.dto.response.RegistrationResponse;
import com.example.backdrop.data.model.User;

import java.io.IOException;

public interface UserService {
    RegistrationResponse register(RegistrationRequest registrationRequest);
    String addBankAccount(AddBankAccountRequest addBankAccountRequest) throws IOException;
    User findUserByName(String name);
    boolean verifyNames(String firstName, String secondName);
    String getAccountName(AccountNameRequest accountNameRequest) throws IOException;
}
