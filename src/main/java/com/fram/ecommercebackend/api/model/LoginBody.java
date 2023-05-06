package com.fram.ecommercebackend.api.model;

import com.sun.istack.NotNull;

import javax.validation.constraints.NotBlank;

public class LoginBody {
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
