package com.example.backdrop.service;

import com.example.backdrop.data.dto.request.AccountNameRequest;
import com.example.backdrop.data.dto.request.AddBankAccountRequest;
import com.example.backdrop.data.dto.request.RegistrationRequest;
import com.example.backdrop.data.dto.response.RegistrationResponse;
import com.example.backdrop.data.model.User;
import com.example.backdrop.data.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {
@Autowired
    UserService userService =  new UserServiceImpl();
@Autowired
    UserRepository userRepository ;
    private RegistrationRequest registrationRequest;
    private RegistrationRequest registrationRequest2;
    private AccountNameRequest accountNameRequest;


    @BeforeEach
    void setUp() {
        registrationRequest = new RegistrationRequest("Obuseh Emmanuel MAKULUCHUKWU");
    }
    @Test
    void testThatAUserCanRegister(){
     RegistrationResponse registeredUser =  userService.register(registrationRequest);
        assertEquals("OBUSEH EMMANUEL MAKULUCHUKWU",registeredUser.getName());

    }
    @Test
    void testThatNumberOfUserIncreasesByOneWhenEverANewUserRegisters(){
        registrationRequest2  = new RegistrationRequest("Derek Manuel");
        int numberOfUser = userRepository.findAll().size();
        assertEquals(0,numberOfUser );
        userService.register(registrationRequest2);
        int totalNumberOfUsers = userRepository.findAll().size();
        assertEquals(1, totalNumberOfUsers);
    }

    @Test
    void testToAddBankAccountAndVerifyTheName() throws IOException {
        AddBankAccountRequest addBankAccountRequest = new AddBankAccountRequest(
                "2063942350",
                "033",
                "OBUSEH EMMANUEL MAKULUCHUKWU"
        );
//        userService.register(registrationRequest);
      String addedAccount=  userService.addBankAccount(addBankAccountRequest);
        User foundUserAfterAddingBankAccount = userService.findUserByName("OBUSEH EMMANUEL MAKULUCHUKWU");
        assertTrue(foundUserAfterAddingBankAccount.isVerified());

    }



    @Test
    void testThatTheDistanceBetweenTwoNamesIsLessOrEqualTwoWillReturnTrue() {
        String firstName = "Obuseh Emmanuel Makuluchukwu";
        String secondName = "Obus Emmanuel Makuluchukwu";
        assertTrue(userService.verifyNames(firstName,secondName));
    }
    @Test
    void testThatTheDistanceBetweenTwoNamesWhenGreaterThanTwoWillReturnFalse(){
        String firstName = "Obuseh Emmanuel Makuluchukwu";
        String secondName = "Obus mmnauel Makuluchuwkwu";
        assertFalse(userService.verifyNames(firstName,secondName));
    }

    @Test
    void testThatNameProvidedByUserAndTheApiNameDistanceIsLessOrEqualTwo() throws IOException{
        accountNameRequest = new AccountNameRequest(
                "033",
                "2063942350",
                "Obus Emmanuel Makuluchukwu");
        String accountName = userService.getAccountName(accountNameRequest);
        assertEquals("Obus Emmanuel Makuluchukwu", accountName);
    }
    @Test
    void testThatNameFetchedByAPIIsReturnedIfAccountNameIsNotProvided() throws IOException{
        accountNameRequest = new AccountNameRequest(
                "033",
                "2063942350",
                "Emmanuel Makuluchukwu");
        String accountName = userService.getAccountName(accountNameRequest);
        assertEquals("Obuseh Emmanuel Makuluchukwu", accountName);
    }
}