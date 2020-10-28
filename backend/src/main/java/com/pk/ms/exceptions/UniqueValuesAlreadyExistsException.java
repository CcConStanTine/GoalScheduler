package com.pk.ms.exceptions;

import com.pk.ms.dto.user.UserUpdateDTO;
import com.pk.ms.security.request.SignupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UniqueValuesAlreadyExistsException extends RuntimeException {

    public UniqueValuesAlreadyExistsException(UserUpdateDTO userUpdateDTO) {
        super("User with nick " + userUpdateDTO.getNick() + " already exists. ");
    }
    public UniqueValuesAlreadyExistsException(SignupRequest signupRequest) {
        super("User with nick " + signupRequest.getUsername() + " already exists. ");
    }
    public UniqueValuesAlreadyExistsException(String email) {
        super("User with email " + email + " already exists. ");
    }

}
