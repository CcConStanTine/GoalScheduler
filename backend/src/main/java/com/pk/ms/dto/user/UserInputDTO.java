package com.pk.ms.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// MyScheduleUser DTO for purpose of creating, and updating some of MyScheduleUser attributes
public class UserInputDTO {

    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    @Size(min = 2, max = 50)
    private String nick;

    @NotNull
    @Size(min = 2, max = 100)
    @Email
    private String email;

    @NotNull
    @Size(min = 2, max = 100)
    private String password;

    public UserInputDTO() {
    }

    public UserInputDTO(String firstName, String lastName, String nick, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
