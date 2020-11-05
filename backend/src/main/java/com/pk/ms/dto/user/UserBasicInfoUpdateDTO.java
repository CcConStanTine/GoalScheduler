package com.pk.ms.dto.user;

import javax.validation.constraints.Size;

public class UserBasicInfoUpdateDTO {

    @Size(min = 2, max = 50)
    private String firstName;

    @Size(min = 2, max = 50)
    private String lastName;

    @Size(min = 2, max = 50)
    private String nick;

    public UserBasicInfoUpdateDTO() {
    }

    public UserBasicInfoUpdateDTO(@Size(min = 2, max = 50) String firstName, @Size(min = 2, max = 50) String lastName, @Size(min = 2, max = 50) String nick) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
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
}