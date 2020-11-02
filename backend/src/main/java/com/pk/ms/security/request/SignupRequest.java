package com.pk.ms.security.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignupRequest {

    @NotBlank(message = "This field cannot be empty!")
    @Size(min = 4, max = 30, message = "User name length must be between 4 and 30!")
    private String username;

    @NotBlank(message = "This field cannot be empty!")
    @Size(min = 6, max = 32, message = "Password length must be between 6 and 32!")
    private String password;

    @NotBlank(message = "This field cannot be empty!")
    @Email(message = "This is not an email pattern!")
    @Size(max = 50, message = "Email length cannot be larger than 50!")
    private String email;

    @NotBlank(message = "This field cannot be empty!")
    @Size(max = 50, message = "First name length cannot be larger than 50!")
    private String firstName;

    @NotBlank(message = "This field cannot be empty!")
    @Size(max = 50, message = "Last name length cannot be larger than 50!")
    private String lastName;

    private Set<String> role;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
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
}
