package com.example.backdrop.controller;

import com.example.backdrop.data.dto.request.AccountNameRequest;
import com.example.backdrop.data.dto.request.AddBankAccountRequest;
import com.example.backdrop.data.dto.request.RegistrationRequest;
import com.example.backdrop.data.dto.response.RegistrationResponse;
import com.example.backdrop.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class UserController {
@Autowired
    private final UserService userService;

    @MutationMapping
    public RegistrationResponse registerUser(@Argument RegistrationRequest registrationRequest){
        return userService.register(registrationRequest);
    }
    @MutationMapping
    public String addBankAccount(@Argument AddBankAccountRequest addBankAccountRequest) throws IOException {
        return userService.addBankAccount(addBankAccountRequest);
    }
    @QueryMapping
    public String getAccountName(@Argument AccountNameRequest accountNameRequest) throws IOException {
        return userService.getAccountName(accountNameRequest);
    }

}
