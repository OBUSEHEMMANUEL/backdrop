package com.example.backdrop.service;

import com.example.backdrop.data.dto.request.AddBankAccountRequest;
import com.example.backdrop.data.dto.request.AccountNameRequest;
import com.example.backdrop.data.dto.request.RegistrationRequest;
import com.example.backdrop.data.dto.response.RegistrationResponse;
import com.example.backdrop.data.dto.response.VerificationResponse;
import com.example.backdrop.data.model.User;
import com.example.backdrop.data.repository.UserRepository;
import com.example.backdrop.exception.UserException;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    private final String key = "sk_live_477dc8ab7aee59ed474800055c0f90594671514c";

    @Override
    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        if (userRepository.findUserByNameIgnoreCase(registrationRequest.name()).isPresent())
            throw new UserException(registrationRequest.name()+ " already exist.");
        User user =new User();
                user.setName(registrationRequest.name());
        User savedUser = userRepository.save(user);
        RegistrationResponse response = new RegistrationResponse();
        response.setName(savedUser.getName());
        response.setMessage("Registration Successful");
        return response;
    }

    @Override
    public String addBankAccount(AddBankAccountRequest addBankAccountRequest) throws IOException {
        User foundUser = findUserByName(addBankAccountRequest.accountName());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.paystack.co/bank/resolve?account_number="
                        + addBankAccountRequest.accountNumber() + "&bank_code="
                        + addBankAccountRequest.bankCode())
                .get()
                .addHeader("Authorization", "Bearer " + key)
                .build();
        try (ResponseBody response = client.newCall(request).execute().body()) {
            Gson gson = new Gson();
             String payStackAccountName = gson.fromJson(response.string(), VerificationResponse.class).getData().getAccount_name();
boolean matches = verifyNames(addBankAccountRequest.accountName(),payStackAccountName);
                    var verified =    foundUser.isVerified();
                    if (matches && !verified) userRepository.isVerified(foundUser.getName());

            return findUserByName(addBankAccountRequest.accountName()).isVerified() + " is verified" ;
        }

        }


    @Override
    public User findUserByName(String name) {
        return userRepository.findUserByNameIgnoreCase(name).orElseThrow(()->new UserException("User not found"));
    }

    @Override
    public boolean verifyNames(String firstName, String secondName) {
        LevenshteinDistance ld = new LevenshteinDistance();
        int distance = ld.apply(firstName.toLowerCase(), secondName.toLowerCase());
        return distance<=2;
    }

    @Override
    public String getAccountName(AccountNameRequest accountNameRequest) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String userName = accountNameRequest.accountName();
        Request request = new Request.Builder()
                .url("https://api.paystack.co/bank/resolve?account_number="
                        + accountNameRequest.accountNumber()+"&bank_code="
                        + accountNameRequest.bankCode())
                .get()
                .addHeader("Authorization", "Bearer "+key)
                .build();
        try(ResponseBody response = client.newCall(request).execute().body()) {
            Gson gson = new Gson();
            String payStackAccountName = gson.fromJson(response.string(), VerificationResponse.class).getData().getAccount_name();

            if (userName!= null && verifyNames(userName, payStackAccountName))return userName;
            else return toSentenceCase(payStackAccountName);
        }
        }
        private String toSentenceCase(String word){
        if(word == null || word.isEmpty()){
            return word;
        }
        String[] letters = word.split("\\s+");
        StringBuilder stringBuilder = new StringBuilder();
        for (String letter : letters){
            if(!letter.isEmpty()){
                stringBuilder.append(Character.toUpperCase(letter.charAt(0)));
                stringBuilder.append( letter.substring(1).toLowerCase());
                stringBuilder.append(" ");
            }

        }
        return stringBuilder.toString().trim();
        }

}
