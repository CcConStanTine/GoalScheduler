package com.pk.ms.dto.user;

// MyScheduleUser DTO for purpose of displaying his information without the Schedule
public class UserInfoDTO {

    private long userId;
    private String firstName;
    private String lastName;
    private String nick;
    private String email;

    public UserInfoDTO() {
    }

    public UserInfoDTO(long userId, String firstName, String lastName, String nick, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
