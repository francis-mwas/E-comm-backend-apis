package com.fram.ecommercebackend.api.auth;


import com.fram.ecommercebackend.api.model.RegistrationBody;
import com.fram.ecommercebackend.exception.UserAlreadyExistsException;
import com.fram.ecommercebackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;
    //constructor injection
    public AuthenticationController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody  RegistrationBody registrationBody){
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException e) {
           return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
