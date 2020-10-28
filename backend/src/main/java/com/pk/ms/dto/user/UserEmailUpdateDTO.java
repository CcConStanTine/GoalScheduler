package com.pk.ms.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEmailUpdateDTO {

    @NotNull
    @Size(min = 2, max = 100)
    @Email
    private String email;

    public UserEmailUpdateDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
