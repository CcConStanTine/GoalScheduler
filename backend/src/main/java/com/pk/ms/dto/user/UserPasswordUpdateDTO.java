package com.pk.ms.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserPasswordUpdateDTO {

    @NotBlank(message = "This field cannot be empty!")
    @Size(min = 6, max = 32, message = "Password length must be between 6 and 32!")
    private String password;

    public UserPasswordUpdateDTO() {
    }

    public UserPasswordUpdateDTO(@NotBlank(message = "This field cannot be empty!") @Size(min = 6, max = 32, message = "Password length must be between 6 and 32!") String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
