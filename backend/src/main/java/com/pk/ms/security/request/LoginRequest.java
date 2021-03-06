package com.pk.ms.security.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "This field cannot be empty!")
    private String username;

    @NotBlank(message = "This field cannot be empty!")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
