package com.fram.ecommercebackend.api.auth;


import com.fram.ecommercebackend.api.model.LoginBody;
import com.fram.ecommercebackend.api.model.LoginResponse;
import com.fram.ecommercebackend.api.model.RegistrationBody;
import com.fram.ecommercebackend.exception.UserAlreadyExistsException;
import com.fram.ecommercebackend.model.LocalUser;
import com.fram.ecommercebackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity loginUser(@Valid @RequestBody LoginBody loginBody){
        String jwt = userService.loginUser(loginBody);
        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwt(jwt);
            return ResponseEntity.ok(loginResponse);
        }

    }

    @GetMapping("/me")
    public LocalUser getUserLoggedInProfile(@AuthenticationPrincipal LocalUser user){
        return user;
    }
}
